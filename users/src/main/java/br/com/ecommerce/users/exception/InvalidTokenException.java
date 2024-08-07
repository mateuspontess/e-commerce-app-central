package br.com.ecommerce.users.exception;

public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT = "Invalid token";


	public InvalidTokenException() {
		super(DEFAULT);
	}

	public InvalidTokenException(String message) {
		super(message);
	}
}