package com.ulfric.rules.suite;

import com.ulfric.rules.Trigger;
import com.ulfric.rules.Variable;

public class Var extends Variable {

	private final String value;

	public Var(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String apply(Trigger trigger) {
		return value;
	}

}