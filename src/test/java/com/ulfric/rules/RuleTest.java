package com.ulfric.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.google.common.truth.Truth;

import com.ulfric.rules.script.Script;
import com.ulfric.veracity.Veracity;

import java.math.BigDecimal;
import java.util.UUID;

class RuleTest {

	private Script script;

	@BeforeEach
	void setup() throws Exception {
		script = Mockito.mock(Script.class);
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(true);
	}

	@Test
	void testGetRule() {
		String name = UUID.randomUUID().toString();
		Rule rule = new Rule(name, script);
		Truth.assertThat(rule.getRule()).isEqualTo(name);
	}

	@Test
	void testTest() throws Exception {
		Rule rule = new Rule("true", script);
		Trigger trigger = new Trigger();
		rule.test(trigger);
		ArgumentCaptor<Trigger> captor = ArgumentCaptor.forClass(Trigger.class);
		Mockito.verify(script, Mockito.times(1)).apply(captor.capture());
		Truth.assertThat(captor.getValue()).isEqualTo(trigger);
	}

	@Test
	void testRuleReturnsResult() {
		Object expected = Result.PASSED;
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(expected);
		Rule rule = new Rule("true", script);
		Truth.assertThat(rule.test(null)).isSameAs(expected);
	}

	@Test
	void testRuleReturnsTrue() {
		Rule rule = new Rule("true", script);
		Truth.assertThat(rule.test(null)).isSameAs(Result.PASSED);
	}

	@Test
	void testRuleReturnsFalse() {
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(false);
		Rule rule = new Rule("true", script);
		Truth.assertThat(rule.test(null)).isSameAs(Result.FAILED);
	}

	@Test
	void testRuleReturnsBigDecimalZero() {
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(BigDecimal.ONE);
		Rule rule = new Rule("true", script);
		Truth.assertThat(rule.test(null)).isSameAs(Result.PASSED);
	}

	@Test
	void testRuleReturnsBigDecimalOne() {
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(BigDecimal.ZERO);
		Rule rule = new Rule("true", script);
		Truth.assertThat(rule.test(null)).isSameAs(Result.FAILED);
	}

	@Test
	void testRuleReturnsNullThrowsException() {
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(null);
		Rule rule = new Rule("true", script);
		Veracity.assertThat(() -> rule.test(null)).doesThrow(IllegalStateException.class);
	}

	@Test
	void testRuleReturnsIllegalThrowsException() {
		Mockito.when(script.apply(ArgumentMatchers.any())).thenReturn(new Object());
		Rule rule = new Rule("true", script);
		Veracity.assertThat(() -> rule.test(null)).doesThrow(IllegalStateException.class);
	}

}