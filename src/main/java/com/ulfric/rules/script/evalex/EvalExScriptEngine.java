package com.ulfric.rules.script.evalex;

import com.udojava.evalex.Expression;
import com.udojava.evalex.Expression.LazyNumber;

import com.ulfric.rules.Variable;
import com.ulfric.rules.script.Script;
import com.ulfric.rules.script.ScriptEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvalExScriptEngine implements ScriptEngine {

	private static final Pattern VARIABLE = Pattern.compile("[:][a-zA-Z0-9_]+[:]");

	private final Map<String, Variable> variables = new HashMap<>();

	@Override
	public Script compile(String script) {
		Objects.requireNonNull(script, "script");

		Set<Variable> variablesInScript = new HashSet<>();
		Matcher matcher = VARIABLE.matcher(script);
		while (matcher.find()) {
			String group = matcher.group();
			String formatted = group.replace(":", "");
			variablesInScript.add(getVariable(formatted));
			script = script.replace(group, formatted + "()");
		}

		return createScript(script, variablesInScript);
	}

	private Variable getVariable(String name) {
		Variable variable = variables.get(name);
		if (variable == null) {
			throw new IllegalArgumentException("Illegal variable: " + name);
		}
		return variable;
	}

	private EvalExScript createScript(String script, Set<Variable> variablesInScript) {
		script = script.replace("!", "0 !");
		Expression expression = new Expression(script);
		expression.addOperator(expression.new Operator("!", 0, true) {
			@Override
			public BigDecimal eval(BigDecimal ignore, BigDecimal bool) {
				return BigDecimal.ZERO.compareTo(bool) == 0 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		Context context = new Context();

		for (Variable variable : variablesInScript) {
			expression.addLazyFunction(expression.new LazyFunction(variable.getName(), 0) {

				@Override
				public LazyNumber lazyEval(List<LazyNumber> arguments) {
					return new LazyVariable(variable.apply(context.getTrigger()));
				}

			});
		}

		return new EvalExScript(expression, context);
	}

	@Override
	public void put(Variable variable) {
		Objects.requireNonNull(variable, "variable");

		variables.put(variable.getName(), variable);
	}

	@Override
	public void remove(Variable variable) {
		variables.remove(variable.getName(), variable);
	}

	private static class LazyVariable implements LazyNumber {

		private final String value;

		LazyVariable(String value) {
			this.value = value;
		}

		@Override
		public BigDecimal eval() {
			String value = getString();
			if (value == null) {
				return BigDecimal.ZERO;
			}
			String lower = value.toLowerCase();
			if (lower.equals("true")) {
				return BigDecimal.ONE;
			} else if (lower.equals("false")) {
				return BigDecimal.ZERO;
			}
			return new BigDecimal(value);
		}

		@Override
		public String getString() {
			return value;
		}

	}

}