package com.ulfric.rules;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

class UncompiledRuleTest {

	@Test
	void testSetsFields() {
		UncompiledRule rule = new UncompiledRule("1", "2");
		Truth.assertThat(rule.getRule()).isEqualTo("1");
		Truth.assertThat(rule.getScript()).isEqualTo("2");
	}

}