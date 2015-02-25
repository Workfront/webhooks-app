package webhooks.rest.resources;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 */
public class DocumentListResponse {
	@JsonProperty("items")
	private List<DocumentResponse> items = new ArrayList<DocumentResponse>();

	public List<DocumentResponse> getDocuments() {
		return items;
	}

	public void setDocuments (List<DocumentResponse> documents) {
		this.items = documents;
	}
}
