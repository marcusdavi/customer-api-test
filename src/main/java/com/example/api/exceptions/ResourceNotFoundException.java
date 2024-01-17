package com.example.api.exceptions;

public class ResourceNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
