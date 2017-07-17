package com.ulfric.rules;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.veracity.suite.BeanTestSuite;

@RunWith(JUnitPlatform.class)
class TriggerTest extends BeanTestSuite<Trigger> {

	TriggerTest() {
		super(Trigger.class);
	}

}