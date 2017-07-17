package com.ulfric.rules.security;

import com.ulfric.rules.RuleEngine;
import com.ulfric.rules.UncompiledRule;

public class MaxLengthSecurityScanner implements SecurityScanner {

	public static MaxLengthSecurityScanner of(int maxLength) {
		if (maxLength < 1) {
			throw new IllegalArgumentException("maxLength must be positive, was " + maxLength);
		}

		return new MaxLengthSecurityScanner(maxLength);
	}

	private final int maxLength;

	private MaxLengthSecurityScanner(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public boolean test(RuleEngine engine, UncompiledRule rule) {
		return rule.getRule().length() <= maxLength;
	}

}