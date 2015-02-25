package webhooks.core.services.util;

import webhooks.core.models.entities.*;

import java.util.*;

/**
 */
public class DocumentList {
	private List<Document> documents = new ArrayList<Document>();

	public DocumentList() {}

	public DocumentList(List<Document> documents) {
		this.documents = documents;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
