package com.ulfric.rules;

import com.ulfric.commons.value.Bean;
import com.ulfric.rules.script.Script;

import java.math.BigDecimal;
import java.util.Objects;

public final class Rule extends Bean {

	private final String rule;
	private final transient Script script;

	Rule(String rule, Script script) {
		Objects.requireNonNull(rule, "rule");
		Objects.requireNonNull(script, "script");

		this.rule = rule;
		this.script = script;
	}

	public String getRule() {
		return rule;
	}

	public Result test(Trigger trigger) {
		Object value = script.apply(trigger);

		if (value instanceof Result) {
			return (Result) value;
		}

		if (value instanceof Boolean) {
			return (boolean) value ? Result.PASSED : Result.FAILED;
		}

		if (value instanceof BigDecimal) {
			BigDecimal bigDecimalValue = (BigDecimal) value;
			return BigDecimal.ONE.compareTo(bigDecimalValue) == 0 ? Result.PASSED : Result.FAILED;
		}

		if (value == null) {
			throw new IllegalStateException(rule + " did not give a result");
		}

		throw new IllegalStateException(rule + " gave illegal result: " + value);
	}

}