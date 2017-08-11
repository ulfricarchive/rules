package com.ulfric.rules.script;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

import com.ulfric.veracity.suite.EnumTestSuite;

class StandardScriptEngineFactoriesTest extends EnumTestSuite {

	StandardScriptEngineFactoriesTest() {
		super(StandardScriptEngineFactories.class);
	}

	@Test
	void testDefaultIsNotNull() {
		Truth.assertThat(StandardScriptEngineFactories.getDefaultScriptEngine()).isNotNull();
	}

}