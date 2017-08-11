package com.ulfric.rules.consequence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

import com.ulfric.rules.Result;
import com.ulfric.rules.Trigger;
import com.ulfric.veracity.suite.EnumTestSuite;

class StandardConsequencesTest extends EnumTestSuite {

	private Trigger trigger;

	StandardConsequencesTest() {
		super(StandardConsequences.class);
	}

	@BeforeEach
	void setup() {
		trigger = new Trigger();
	}

	@Test
	void testBlockFailsTarget() {
		StandardConsequences.BLOCK.apply(trigger);
		Truth.assertThat(trigger.getResult()).isSameAs(Result.FAILED);
	}

	@Test
	void testAllowExplicitlyAllowsTarget() {
		StandardConsequences.ALLOW.apply(trigger);
		Truth.assertThat(trigger.getResult()).isSameAs(Result.EXPLICITLY_ALLOWED);
	}

}