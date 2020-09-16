package com.hit.exception;

public class UnknownIdException extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	public UnknownIdException(Throwable err, String message) {
		super(message,err);

	}
	public UnknownIdException(java.lang.Throwable err) {
		super(err);
	}
}
