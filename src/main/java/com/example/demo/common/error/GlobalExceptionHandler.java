package com.example.demo.common.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
		log.error("CustomException => {}", e.getMessage(), e);
		return ResponseEntity
			.status(e.getStatus())
			.body(ErrorResponse.of(e.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
		log.error("IllegalArgumentException => {}", e.getMessage(), e);
		return ResponseEntity
			.status(BAD_REQUEST)
			.body(ErrorResponse.of(e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException => {}", e.getBindingResult().getFieldError().getDefaultMessage());
		return ResponseEntity
			.status(BAD_REQUEST)
			.body(ErrorResponse.of(e.getBindingResult().getFieldError().getDefaultMessage()));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		log.error("Exception => {}", e.getMessage(), e);
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.of("서버에서 에러가 발생하였습니다."));
	}
}
