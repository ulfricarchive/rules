package com.ulfric.rules;

public class RuleException extends RuntimeException {

	public RuleException(String message) {
		super(message);
	}

	public RuleException(Throwable thrown) {
		super(thrown);
	}

}