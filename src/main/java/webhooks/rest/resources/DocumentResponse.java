package webhooks.rest.resources;

import org.codehaus.jackson.annotate.*;
import webhooks.core.models.entities.*;
import webhooks.core.services.util.*;

import java.util.*;

/**
 */
public class DocumentResponse {

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

	public String getId() {
		return id;
	}

	public DocumentResponse setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public DocumentResponse setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getMimeType() {
		return mimeType;
	}

	public DocumentResponse setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	public long getSize() {
		return size;
	}

	public DocumentResponse setSize(long size) {
		this.size = size;
		return this;
	}

	public String getKind() {return kind;}

	public DocumentResponse setKind(String type){
		this.kind = type;
		return this;
	}

	public String getDownloadLink(){
		return downloadLink;
	}

	public DocumentResponse setDownloadLink(String downloadUrl) {
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
		return "DocumentResponse {" +
			"type='" + mimeType + '\'' +
			", name='" + title + '\'' +
			", id='" + id + '\'' +
			'}';
	}
}
