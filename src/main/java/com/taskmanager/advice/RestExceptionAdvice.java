package com.taskmanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.taskmanager.dto.ErrorResponse;
import com.taskmanager.exception.AlreadyExistsException;
import com.taskmanager.exception.NotFoundException;

@RestControllerAdvice
public class RestExceptionAdvice {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse exceptionHandler(NotFoundException exception) {
		return new ErrorResponse(exception.getMessage());
	}

	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse exceptionHandler(AlreadyExistsException exception) {
		return new ErrorResponse(exception.getMessage());
	}
}
