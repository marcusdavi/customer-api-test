package com.example.api.exception.handler;


public class Field {

    private String name;
    private String message;

    public Field(String name, String message) {
	super();
	this.name = name;
	this.message = message;
    }

    public String getName() {
	return name;
    }

    public String getMessage() {
	return message;
    }

}
