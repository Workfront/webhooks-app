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
