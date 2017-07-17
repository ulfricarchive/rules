package com.ulfric.rules;

import com.ulfric.commons.value.Bean;

public final class UncompiledRule extends Bean {

	private final String rule;
	private final String script;

	UncompiledRule(String rule, String script) {
		this.rule = rule;
		this.script = script;
	}

	public String getRule() {
		return rule;
	}

	public String getScript() {
		return script;
	}

}