package com.eca.visitor.exception;

public class VisitorManagementException extends RuntimeException{

	public VisitorManagementException(String message) {
		super(message);
	}
	public VisitorManagementException(String message, Throwable throwable) {
		super(message,throwable);
	}


}
