/**
 * Copyright 2014 AtTask, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package webhooks.core.services.impl;

import org.apache.commons.io.*;
import org.apache.tika.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import webhooks.config.*;
import webhooks.core.models.entities.*;
import webhooks.core.repositories.*;
import webhooks.core.services.*;
import webhooks.core.services.exceptions.*;
import webhooks.core.services.util.*;

import javax.transaction.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Document service implementation
 */
@Service
@Transactional
public class DocumentServiceImp implements IDocumentService {

	@Autowired
	private IDocumentRepo documentRepo;

	@Override
	public Document getMetaData(String documentID) {
		String path = DocumentUtil.getLocationFromDocId(documentID);
		File targetFile = new File(path);
		if (targetFile.exists()) {
			try {
				Document doc= new Document();
				doc.setId(documentID);
				doc.setMimeType(new Tika().detect(targetFile));
				doc.setLocation(path);
				doc.setKind(targetFile.isFile() ? Document.TYPE_FILE : Document.TYPE_FOLDER);
				doc.setSize(targetFile.length());
				doc.setLastModified(targetFile.lastModified());
				doc.setName(targetFile.getName());

				return doc;
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public Document uploadStart(String parentDocumentId, String documentName) throws DocumentExistsException {
		String path = DocumentUtil.getLocationFromDocId(parentDocumentId);

		// make the path for the new document
		path += File.separator + documentName;
		File file = new File(path);
		if (file.exists())
			throw new DocumentExistsException(path);

		Document doc = new Document();
		doc.setKind(Document.TYPE_FILE);
		//doc.getMimeType();
		doc.setId(DocumentUtil.GenerateDocId(path));

		return doc;
	}


	@Override
	public boolean uploadEnd(String documentID, InputStream stream) {
		boolean ret = false;
		String path = DocumentUtil.getLocationFromDocId(documentID);

		File targetFile = null;

		try {
			// check the path
			targetFile = new File(path);
			if (targetFile.exists()){
				throw new DocumentExistsException();
			}

			if (targetFile.createNewFile()) {
				byte[] bytes = new byte[2048];
				int bytesRead = 0;
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(targetFile));
				while ((bytesRead = stream.read(bytes)) != -1) {
					os.write(bytes, 0, bytesRead);
				}
				os.flush();
				os.close();

				ret = true;
			}
		} catch (Exception e) {
			if (targetFile != null && targetFile.exists()) {
				targetFile.delete();
			}
		}

		return ret;
	}


	@Override
	public DocumentList getFiles(String parentDocumentId, int max, int offset) {
		String path = DocumentUtil.getLocationFromDocId(parentDocumentId);
		return listFilesForFolder(new File(path), max, offset);
	}

	@Override
	public Document getDocumentByID(String documentId) {
		//return documentRepo.findDocumentByID(documentId);
		//implement without database for this case
		String path = DocumentUtil.getLocationFromDocId(documentId);
		File entry = new File(path);
		return convertToDocument(entry);
	}

	@Override
	public DocumentList findAllDocuments() {
		List<Document> documentList = documentRepo.findAllDocuments();
		return new DocumentList(documentList);
	}


	/**
	 *	return all documents/folder from published paths
	 * @param max
	 * @param offset
	 * @return
	 */
	@Override
	public DocumentList getRootFiles(int max, int offset) {
		List<Document> finalList = new ArrayList<Document>();

		DocumentList ret = null;
		int oldMax = max;

		for (String path : ConfigBean.getInstance().getPaths()) {
			try {
				String normalize = FilenameUtils.normalize(path);
				if (normalize.startsWith("~/")){
					normalize = System.getProperty("user.home") + normalize.substring(1);
				} else if (normalize.startsWith("~")) {
					normalize = System.getProperty("user.home") + File.separator + normalize.substring(1);
				}

				ret = listFilesForFolder(new File(normalize), max, offset);
				if (ret.getDocuments().size() > 0) {
					finalList.addAll(finalList.size(), ret.getDocuments());
					offset = 0;
					max = max - ret.getDocuments().size();
				}

				if (finalList.size() == oldMax)
					break;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new DocumentList(finalList);
	}

	@Override
	public DocumentList findFiles(String query, int max, int offset) {
		List<Document> finalList = new ArrayList<Document>();

		List<Document> ret = null;
		int oldMax = max;

		for (String path : ConfigBean.getInstance().getPaths()) {
			try {
				String normalize = FilenameUtils.normalize(path);
				if (normalize.startsWith("~/")){
					normalize = System.getProperty("user.home") + normalize.substring(1);
				} else if (normalize.startsWith("~")) {
					normalize = System.getProperty("user.home") + File.separator + normalize.substring(1);
				}

				ret = traverse(normalize, query, max, offset);
				if (ret.size() > 0) {
					finalList.addAll(finalList.size(), ret);
					offset = 0;
					max = max - ret.size();
				}

				if (finalList.size() == oldMax)
					break;

			} catch (Exception e){

			}
		}

		return new DocumentList(finalList);
	}

	public List<Document> traverse(String path, String query, int max, int offset) {
		Path startingDir =  Paths.get(path);
		List<Document> resList = new ArrayList<Document>();
		int skipped = 0;

		FileFinder fileFinder = new FileFinder(query, max, offset);
		try {
			Files.walkFileTree(startingDir, fileFinder);
			ArrayList<File> files = fileFinder.getMatched();
			for (final File fileEntry : files) {
				if (resList.size() == max)
					break;

				if (skipped < offset) {
					skipped++;
					continue;
				}

				Document doc = convertToDocument(fileEntry);
				if (doc != null){
					resList.add(doc);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resList;
	}

	@Override
	public Document addDocument(Document data) {
		return documentRepo.createDocument(data);
	}

	private DocumentList listFilesForFolder(final File folder, int max, int offset) {
		List<Document> resList = new ArrayList<Document>();
		int skipped = 0;

		if (folder.exists()) {
			for (final File fileEntry : folder.listFiles()) {
				if (max > 0 && resList.size() == max)
					break;

				if (skipped < offset) {
					skipped++;
					continue;
				}

				Document doc = convertToDocument(fileEntry);
				if (doc != null)
					resList.add(doc);
			}
		}
		return new DocumentList(resList);
	}

	// use apache tika to detect mime type. the java implementation probContentType always returned null for OSX?????
	private String getMimeType(Path path) throws IOException {
		Tika tika = new Tika();
		return tika.detect(path.toFile());
	}

	private Document convertToDocument(File fileEntry) {
		Document doc = null;
		if (!fileEntry.isHidden()) {
			doc = new Document();
			doc.setName(fileEntry.getName());
			//doc.setLocation(fileEntry.getPath());
			String now = String.valueOf(new Date().getTime());
			// fullpath + # + timestamp(long)
			doc.setId(CipherUtil.encrypt(fileEntry.getPath() + "#" + now));
			// get mime type of a document
			if (fileEntry.isFile()) {
				try {
					String mimeType = getMimeType(fileEntry.toPath());
					doc.setMimeType(mimeType);
				} catch (IOException e) {
					e.printStackTrace();
				}

				doc.setSize(fileEntry.length());
				doc.setKind(Document.TYPE_FILE);
			} else {
				doc.setKind(Document.TYPE_FOLDER);
			}
			doc.setLastModified(fileEntry.lastModified());
		}

		return doc;
	}
}
