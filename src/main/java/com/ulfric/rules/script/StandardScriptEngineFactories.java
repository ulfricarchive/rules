package com.ulfric.rules.script;

import com.ulfric.rules.script.evalex.EvalExScriptEngine;

public enum StandardScriptEngineFactories implements ScriptEngineFactory {

	EVALEX(EvalExScriptEngine::new);

	public static ScriptEngine getDefaultScriptEngine() {
		return StandardScriptEngineFactories.values()[0].get();
	}

	private final ScriptEngineFactory factory;

	StandardScriptEngineFactories(ScriptEngineFactory factory) {
		this.factory = factory;
	}

	@Override
	public ScriptEngine get() {
		return factory.get();
	}

}