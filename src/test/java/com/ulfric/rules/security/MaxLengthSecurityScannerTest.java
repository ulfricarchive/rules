package com.ulfric.rules.security;

import org.junit.jupiter.api.Test;

import com.google.common.truth.BooleanSubject;
import com.google.common.truth.Truth;

import com.ulfric.dragoon.reflect.Instances;
import com.ulfric.rules.UncompiledRule;
import com.ulfric.veracity.Veracity;

class MaxLengthSecurityScannerTest {

	@Test
	void testIllegalSize() {
		Veracity.assertThat(() -> MaxLengthSecurityScanner.of(0)).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testDoesNotBlockWithinBounds() {
		ruleCheck("a", 1).isTrue();
	}

	@Test
	void testBlocksOutOfBounds() {
		ruleCheck("aaa", 1).isFalse();
	}

	private BooleanSubject ruleCheck(String rule, int maxLength) {
		SecurityScanner scanner = MaxLengthSecurityScanner.of(maxLength);
		UncompiledRule test = Instances.instance(UncompiledRule.class, rule, rule);
		return Truth.assertThat(scanner.test(null, test));
	}

}