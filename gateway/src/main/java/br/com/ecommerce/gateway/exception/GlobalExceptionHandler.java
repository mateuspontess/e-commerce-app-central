package br.com.ecommerce.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorMessage> handlerResponseStatusException(ResponseStatusException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getStatusCode().value(), ex.getReason());
		return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
	}

	@ExceptionHandler(WebClientResponseException.Unauthorized.class)
	public ResponseEntity<ErrorMessage> handlerResponseStatusException(WebClientResponseException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorMessage> handlerRuntimeException(RuntimeException ex) {
		ex.printStackTrace();
		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}

	private record ErrorMessage(int status, String message) {}
}