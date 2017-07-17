package com.ulfric.rules.consequence;

import com.ulfric.rules.Result;
import com.ulfric.rules.Trigger;

public enum StandardConsequences implements Consequence {

	ALLOW {
		@Override
		public void apply(Trigger trigger) {
			trigger.setResult(Result.EXPLICITLY_ALLOWED);
		}
	},

	BLOCK {
		@Override
		public void apply(Trigger trigger) {
			trigger.setResult(Result.FAILED);
		}
	};

}
