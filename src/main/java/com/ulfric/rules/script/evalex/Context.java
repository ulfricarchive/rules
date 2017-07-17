package com.ulfric.rules.script.evalex;

import com.ulfric.commons.value.Bean;
import com.ulfric.rules.Trigger;

final class Context extends Bean {

	private Trigger trigger;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

}