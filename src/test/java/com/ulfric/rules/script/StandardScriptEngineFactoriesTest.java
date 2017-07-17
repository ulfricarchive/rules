package com.ulfric.rules.script;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;

import com.ulfric.veracity.suite.EnumTestSuite;

@RunWith(JUnitPlatform.class)
class StandardScriptEngineFactoriesTest extends EnumTestSuite {

	StandardScriptEngineFactoriesTest() {
		super(StandardScriptEngineFactories.class);
	}

	@Test
	void testDefaultIsNotNull() {
		Truth.assertThat(StandardScriptEngineFactories.getDefaultScriptEngine()).isNotNull();
	}

}