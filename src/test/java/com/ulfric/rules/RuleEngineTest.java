package com.ulfric.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;

import com.ulfric.rules.security.MaxLengthSecurityScanner;
import com.ulfric.rules.security.SecurityScanner;
import com.ulfric.rules.suite.X;
import com.ulfric.veracity.Veracity;

@RunWith(JUnitPlatform.class)
class RuleEngineTest {

	private RuleEngine engine;

	@BeforeEach
	void setupRuleEngine() {
		engine = new RuleEngine();
	}

	@Test
	void testCompileSimpleRule() {
		engine.addVariable(new X("true"));
		Rule rule = engine.compile(":x:");
		Truth.assertThat(rule.test(null)).isEqualTo(Result.PASSED);
	}

	@Test
	void testVariableRemoval() {
		X x = new X();
		engine.addVariable(x);
		engine.removeVariable(x);
		Veracity.assertThat(() -> engine.compile(":x:").test(null)).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testVariableNonExistentVariable() {
		X x = new X();
		engine.removeVariable(x);
	}

	@Test
	void testCompileFailsSecurityScanner() {
		engine.addSecurityScanner(MaxLengthSecurityScanner.of(1));
		engine.addVariable(new X());
		Veracity.assertThat(() -> engine.compile(":x:").test(null)).doesThrow(RuleException.class);
	}

	@Test
	void testCompilePassesSecurityScanner() {
		engine.addSecurityScanner(MaxLengthSecurityScanner.of(3));
		engine.addVariable(new X());
		Veracity.assertThat(() -> engine.compile(":x:").test(null)).runsWithoutExceptions();
	}

	@Test
	void testSecurityScannerRemoval() {
		SecurityScanner scanner = MaxLengthSecurityScanner.of(1);
		engine.addSecurityScanner(scanner);
		engine.removeSecurityScanner(scanner);
		engine.addVariable(new X());
		Veracity.assertThat(() -> engine.compile(":x:").test(null)).runsWithoutExceptions();
	}

}