package webhooks.core.repositories;

import webhooks.core.models.entities.*;

import java.util.*;

/**
 */
public interface IDocumentRepo {
	public Document createDocument(Document data);
	public Document findDocumentByID (String id);
	public List<Document> findAllDocuments();
}
