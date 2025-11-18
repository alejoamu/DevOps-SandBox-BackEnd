package com.icesi.devopssandboxbackend.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		List<String> details = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.toList();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildApiError(HttpStatus.BAD_REQUEST, "Validation failed", details));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
	}

	private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, List<String> details) {
		return ResponseEntity.status(status).body(buildApiError(status, message, details));
	}

	private ApiError buildApiError(HttpStatus status, String message, List<String> details) {
		var error = new ApiError();
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(message);
		error.setDetails(details);
		return error;
	}
}

