package com.example.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageErrorEnum {
	
	CUSTOMER_NOT_FOUND("The customer doesn't exist."),
	ADDRESS_NOT_FOUND("One or more addresses don't exist.");
	
	private String message;

}
