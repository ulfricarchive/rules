package com.ulfric.rules;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.veracity.suite.ConstructTestSuite;

@RunWith(JUnitPlatform.class)
class RuleExceptionTest extends ConstructTestSuite {

	RuleExceptionTest() {
		super(RuleException.class);
	}

}