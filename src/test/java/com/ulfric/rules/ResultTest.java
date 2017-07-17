package com.ulfric.rules;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.veracity.suite.EnumTestSuite;

@RunWith(JUnitPlatform.class)
class ResultTest extends EnumTestSuite {

	ResultTest() {
		super(Result.class);
	}

}
