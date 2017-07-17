package com.ulfric.rules;

import java.util.Objects;
import java.util.function.Function;

public abstract class Variable implements Function<Trigger, String> {

	private final String name;

	public Variable(String name) {
		Objects.requireNonNull(name, "name");

		this.name = name;
	}

	public String getName() {
		return name;
	}

}