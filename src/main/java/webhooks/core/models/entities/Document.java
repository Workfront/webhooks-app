package webhooks.core.models.entities;

import javax.persistence.*;

/**
 * Document entity
 */
@Entity
public class Document {
	public static final String TYPE_FILE = "file";
	public static final String TYPE_FOLDER = "folder";

	@Id
	private String id;

	private String name;
	private String mimeType;
	private String location;
	private long   lastModified;
	private long   size;
	private String kind;  // file or folder (for now)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
