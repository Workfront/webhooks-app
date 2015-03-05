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
