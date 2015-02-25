package webhooks.core.services;


import org.springframework.web.multipart.*;
import webhooks.core.models.entities.*;
import webhooks.core.services.util.*;

import java.io.*;

/**
 */
public interface IDocumentService {
	public Document getMetaData(String documentID);
	public Document uploadStart(String parentDocumentId, String documentName);
	public boolean uploadEnd(String documentID, InputStream stream);
	public DocumentList getFiles(String parentDocumentId, int max, int offset);
	public DocumentList getRootFiles(int max, int offset);
	public DocumentList findFiles(String query, int max, int offset);
	public DocumentList findAllDocuments();
	public Document getDocumentByID(String documentId);
	public Document addDocument(Document data);

}
