package com.ulfric.rules;

import com.ulfric.commons.naming.Named;

import java.util.Objects;
import java.util.function.Function;

public abstract class Variable implements Function<Trigger, String>, Named {

	private final String name;

	public Variable(String name) {
		Objects.requireNonNull(name, "name");

		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}