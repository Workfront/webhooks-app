package webhooks.core.services.exceptions;

/**
 */
public class DocumentDoesNotExistException extends RuntimeException {
	public DocumentDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public DocumentDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentDoesNotExistException(String message) {
		super(message);
	}

	public DocumentDoesNotExistException() {
	}
}
