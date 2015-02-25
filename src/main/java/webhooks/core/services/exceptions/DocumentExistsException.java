package webhooks.core.services.exceptions;

/**
 */
public class DocumentExistsException extends RuntimeException {
	public DocumentExistsException (String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentExistsException (String message) {
		super(message);
	}

	public DocumentExistsException () {
		super();
	}
}
