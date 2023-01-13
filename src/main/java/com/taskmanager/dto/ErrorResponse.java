package com.taskmanager.dto;

public class ErrorResponse {
	private String message;

	public ErrorResponse(String msg) {
		message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
