package com.ulfric.rules.script.evalex;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;

import com.ulfric.rules.suite.X;
import com.ulfric.veracity.Veracity;

import java.math.BigDecimal;

@RunWith(JUnitPlatform.class)
class EvalExScriptEngineTest {

	@Test
	void testVariablesCanBeRemoved() {
		EvalExScriptEngine engine = new EvalExScriptEngine();
		X x = new X();
		engine.put(x);
		engine.remove(x);
		Veracity.assertThat(() -> engine.compile(":x:").apply(null).toString()).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testVariablesAreResolved() {
		EvalExScriptEngine engine = new EvalExScriptEngine();
		X x = new X();
		engine.put(x);
		Truth.assertThat(engine.compile(":x:").apply(null)).isEqualTo(BigDecimal.ONE);
	}

	@Test
	void testVariableNull() {
		EvalExScriptEngine engine = new EvalExScriptEngine();
		X x = new X(null);
		engine.put(x);
		Truth.assertThat(engine.compile(":x:").apply(null)).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	void testVariableFalse() {
		EvalExScriptEngine engine = new EvalExScriptEngine();
		X x = new X("false");
		engine.put(x);
		Truth.assertThat(engine.compile(":x:").apply(null)).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	void testVariableNumber() {
		EvalExScriptEngine engine = new EvalExScriptEngine();
		X x = new X("3");
		engine.put(x);
		Truth.assertThat(engine.compile(":x:").apply(null)).isEqualTo(BigDecimal.valueOf(3));
	}

}