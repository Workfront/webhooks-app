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

import org.codehaus.jackson.annotate.*;
import org.springframework.hateoas.*;
import webhooks.core.models.entities.*;
import webhooks.core.services.util.*;

import java.text.*;
import java.util.*;

/**
 */
public class DocumentResource extends ResourceSupport {

	@JsonProperty("id")
	private String id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("kind")
	private String kind;
	@JsonProperty("mimeType")
	private String mimeType;
	@JsonProperty("downloadLink")
	private String downloadLink;
	@JsonProperty("dateModified")
	private Calendar dateModified;
	@JsonProperty("size")
	private long size;
	@JsonProperty("viewLink")
	private String viewLink;

	public String getDocId() {
		return id;
	}

	public DocumentResource setDocId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public DocumentResource setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getMimeType() {
		return mimeType;
	}

	public DocumentResource setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	public long getSize() {
		return size;
	}

	public DocumentResource setSize(long size) {
		this.size = size;
		return this;
	}

	public String getKind() {return kind;}

	public DocumentResource setKind(String type){
		this.kind = type;
		return this;
	}

	public String getDownloadLink(){
		return downloadLink;
	}

	public DocumentResource setDownloadLink(String downloadUrl) {
		this.downloadLink = downloadUrl;
		return this;
	}

	public Calendar getDateModified() {
		return dateModified;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.dateModified = modifiedDate;
	}

	public String getLocation() {
		String ret = null;
		if (id != null) {
			ret = DocumentUtil.getLocationFromDocId(id);
		}

		return ret;
	}


	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}

	@Override
	public String toString() {
		return "DocumentResource {" +
			"type='" + mimeType + '\'' +
			", name='" + title + '\'' +
			", docId='" + id + '\'' +
			'}';
	}

	public Document toDocument() {
		Document document = new Document();
		document.setId(this.id);
		document.setMimeType(this.getMimeType());
		document.setName(this.getTitle());
		document.setSize(this.getSize());
		document.setKind(this.getKind());
		document.setLocation(this.getLocation());

		long lastModifiedDate = new Date().getTime();
		if (this.getDateModified() != null){
			lastModifiedDate = this.getDateModified().getTime().getTime();
		}
		document.setLastModified(lastModifiedDate);
		return document;
	}

	public DocumentResponse toDocumentResponse() {
		DocumentResponse document = new DocumentResponse();
		document.setId(this.id);
		document.setMimeType(this.getMimeType());
		document.setTitle(this.getTitle());
		document.setSize(this.getSize());
		document.setKind(this.getKind());
		document.setDownloadLink(this.getDownloadLink());
		document.setModifiedDate(this.getDateModified());
		document.setViewLink(this.getViewLink());
		return document;
	}
}
