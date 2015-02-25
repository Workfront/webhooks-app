package webhooks.rest.resources;

import org.codehaus.jackson.annotate.*;

/**
 */
public class UploadResponse {
	public static final String SUCCESS = "success";
	public static final String FAILED = "fail";

	@JsonProperty("result")
	private String result;
	@JsonProperty("message")
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
