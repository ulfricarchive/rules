package com.ulfric.rules.script.evalex;

import com.udojava.evalex.Expression;

import com.ulfric.rules.Trigger;
import com.ulfric.rules.script.Script;

import java.util.Objects;

public class EvalExScript implements Script {

	private final Expression expression;
	private final Context context;

	public EvalExScript(Expression expression, Context context) {
		Objects.requireNonNull(expression, "expression");
		Objects.requireNonNull(context, "context");

		this.expression = expression;
		this.context = context;
	}

	@Override
	public synchronized Object apply(Trigger trigger) {
		context.setTrigger(trigger);
		try {
			return expression.eval();
		} finally {
			context.setTrigger(null);
		}
	}

}