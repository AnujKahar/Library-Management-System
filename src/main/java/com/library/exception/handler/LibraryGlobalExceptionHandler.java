package com.library.exception.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LibraryGlobalExceptionHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(LibraryGlobalExceptionHandler.class);

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, List<String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		LOGGER.error("An MethodArgumentNotValidException occurred while processing the request : {}", e.getMessage(),
				e);
		Map<String, List<String>> errors = new HashMap<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError fe : fieldErrors) {
			String field = fe.getField();
			String errorMsg = fe.getDefaultMessage();
			List<String> errorList = errors.get(field);

			if (errorList == null)
				errorList = new ArrayList<>();
			errorList.add(errorMsg);
			errors.put(field, errorList);
			LOGGER.error("Field name: {}", field);
			LOGGER.error("Field errors: {}", errorList);
		}
		LOGGER.error("exiting methodArgumentNotValidExceptionHandler");
		return errors;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<ResponseMessage> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
		LOGGER.error("An HttpMessageNotReadableException occurred while processing the request : {}", e.getMessage(),
				e);
		ResponseMessage em = new ResponseMessage(400, "JSON Syntax error", new Date());
		LOGGER.error("Responding with status {} and body: {}", HttpStatus.BAD_REQUEST, em);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(em);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ResponseMessage> exceptionHandler(Exception e) {
		LOGGER.error("An Exception occurred while processing the request : {}", e.getMessage(), e);
		ResponseMessage em = new ResponseMessage(500, e.getMessage(), new Date());
		LOGGER.error("Responding with status {} and body: {}", HttpStatus.INTERNAL_SERVER_ERROR, em);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(em);
	}
}
