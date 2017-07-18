package com.ulfric.rules;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.rules.script.ScriptEngine;
import com.ulfric.rules.script.StandardScriptEngineFactories;
import com.ulfric.rules.security.SecurityScanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RuleEngine {

	private static final Pattern SEMANTICS = Pattern.compile("(?i)([\\s])?(AND|OR|NOT)([\\s])?");
	private static final Map<String, String> SEMANTIC_TO_OPERATOR = new CaseInsensitiveMap<>();

	static {
		SEMANTIC_TO_OPERATOR.put("NOT", "!");
		SEMANTIC_TO_OPERATOR.put("OR", "||");
		SEMANTIC_TO_OPERATOR.put("AND", "&&");
	}

	private final ScriptEngine engine;
	private final Map<String, Variable> variables = new HashMap<>();
	private final List<SecurityScanner> scanners = new ArrayList<>();

	public RuleEngine() {
		this(StandardScriptEngineFactories.getDefaultScriptEngine());
	}

	public RuleEngine(ScriptEngine engine) {
		Objects.requireNonNull(engine, "engine");

		this.engine = engine;
	}

	public Rule compile(String rule) {
		String script = replaceSemantics(rule);

		enforceSecurityScanners(rule, script);

		return new Rule(rule, engine.compile(script));
	}

	private String replaceSemantics(String script) {
		String formatted = script;
		Matcher matcher = SEMANTICS.matcher(script);
		while (matcher.find()) {
			formatted = matcher.replaceFirst("$1" + SEMANTIC_TO_OPERATOR.get(matcher.group(2)) + "$3");
		}
		return formatted;
	}

	private void enforceSecurityScanners(String rule, String script) {
		if (!scanners.isEmpty()) {
			UncompiledRule test = new UncompiledRule(rule, script);
			for (SecurityScanner scanner : scanners) {
				if (!scanner.test(this, test)) {
					throw new RuleException(rule + " did not pass security check of " + scanner);
				}
			}
		}
	}

	public void addVariable(Variable variable) {
		Objects.requireNonNull(variable, "variable");

		String name = variable.getName();
		this.variables.put(name, variable);
		engine.put(variable);
	}

	public void removeVariable(Variable variable) {
		Objects.requireNonNull(variable, "variable");

		if (this.variables.remove(variable.getName(), variable)) {
			engine.remove(variable);
		}
	}

	public void addSecurityScanner(SecurityScanner scanner) {
		Objects.requireNonNull(scanner, "scanner");

		scanners.add(scanner);
	}

	public void removeSecurityScanner(SecurityScanner scanner) {
		Objects.requireNonNull(scanner, "scanner");

		scanners.remove(scanner);
	}

}