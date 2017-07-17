package com.ulfric.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.common.truth.Truth;

import com.ulfric.rules.consequence.Consequence;
import com.ulfric.rules.script.Script;
import com.ulfric.veracity.Veracity;

@RunWith(JUnitPlatform.class)
class RuleListTest {

	private RuleList rules;
	private Script script;
	private Consequence consequence;
	private Trigger trigger;

	@BeforeEach
	void setupRuleList() {
		rules = new RuleList();
		script = Mockito.mock(Script.class);
		consequence = Mockito.mock(Consequence.class);
		this.trigger = new Trigger();
		Mockito.when(script.apply(trigger)).thenReturn(Result.PASSED);
		RuleList.Entry entry = RuleList.Entry.builder()
				.addRule(new Rule("", script))
				.addConsequence(consequence)
				.build();
		rules.add(entry);
	}

	@Test
	void testBuildEmptyEntry() {
		Veracity.assertThat(() -> RuleList.Entry.builder().build()).doesThrow(IllegalStateException.class);
	}

	@Test
	void testExplicitlyAllowedEndsExecution() {
		Mockito.when(script.apply(trigger)).thenReturn(Result.EXPLICITLY_ALLOWED);
		Script doNotRun = Mockito.mock(Script.class);
		rules.add(wrap(doNotRun));
		rules.apply(trigger);
		Mockito.verify(doNotRun, Mockito.never()).apply(trigger);
	}

	@Test
	void testFailedEndsExecutionAndAppliesConsequences() {
		Mockito.when(script.apply(trigger)).thenReturn(Result.FAILED);
		rules.apply(trigger);
		Mockito.verify(consequence, Mockito.times(1)).apply(trigger);
	}

	@Test
	void testDefaultsToPassed() {
		Truth.assertThat(rules.apply(trigger)).isSameAs(Result.PASSED);
	}

	private RuleList.Entry wrap(Script script) {
		Rule rule = new Rule("", script);
		return RuleList.Entry.builder()
				.addRule(rule)
				.build();
	}

}
