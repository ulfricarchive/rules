package com.ulfric.rules.script;

import com.ulfric.rules.Variable;

public interface ScriptEngine {

	Script compile(String script);

	void put(Variable variable);

	void remove(Variable variable);

}