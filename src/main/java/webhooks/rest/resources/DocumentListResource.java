package webhooks.rest.resources;

import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.*;

import java.util.*;

/**
 */
public class DocumentListResource extends ResourceSupport {

	@JsonProperty("items")
	private List<DocumentResource> items = new ArrayList<DocumentResource>();

	public DocumentListResource() {}

	public List<DocumentResource> getDocuments() {
		return items;
	}

	public void setDocuments (List<DocumentResource> documents) {
		this.items = documents;
	}

	public DocumentResponse[] toDocumentResponseArray() {
		ArrayList<DocumentResponse> list = new ArrayList<DocumentResponse>();
		for (DocumentResource item : items) {
			DocumentResponse response = item.toDocumentResponse();
			list.add(response);
		}

		return list.toArray(new DocumentResponse[list.size()]);

	}

/*	public DocumentListResponse toDocumentListResponse() {
		ArrayList<DocumentResponse> list = new ArrayList<>();
		for (DocumentResource item : items) {
			DocumentResponse response = item.toDocumentResponse();
			list.add(response);
		}

		DocumentListResponse ret = new DocumentListResponse();
		ret.setDocuments(list);

		return ret;
	}*/
}
