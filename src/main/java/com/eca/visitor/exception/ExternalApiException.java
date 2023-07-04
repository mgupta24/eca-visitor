package com.eca.visitor.exception;

public class ExternalApiException extends RuntimeException {
	public ExternalApiException(String message, Throwable e) {
		super(message,e);
	}

	public ExternalApiException(String message) {
		super(message);
	}
}
