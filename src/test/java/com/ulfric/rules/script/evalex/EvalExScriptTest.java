package com.ulfric.rules.script.evalex;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.udojava.evalex.Expression;

import com.google.common.truth.Truth;

import com.ulfric.rules.Trigger;
import com.ulfric.veracity.Veracity;

import java.math.BigDecimal;

class EvalExScriptTest {

	@Test
	void testApplyEvaluatesScript() {
		Expression expression = Mockito.mock(Expression.class);
		Context context = new Context();
		EvalExScript script = new EvalExScript(expression, context);
		Mockito.when(expression.eval()).thenAnswer(new Answer<BigDecimal>() {
			@Override
			public BigDecimal answer(InvocationOnMock invocation) throws Throwable {
				Truth.assertThat(context.getTrigger()).isNotNull();
				return BigDecimal.ONE;
			}
		});
		Trigger trigger = new Trigger();
		script.apply(trigger);
	}

	@Test
	void testApplyWhereEvalThrows() {
		Expression expression = Mockito.mock(Expression.class);
		Mockito.doThrow(RuntimeException.class).when(expression).eval();
		Context context = new Context();
		EvalExScript script = new EvalExScript(expression, context);
		Veracity.assertThat(() -> script.apply(new Trigger())).doesThrow(RuntimeException.class);
		Truth.assertThat(context.getTrigger()).isNull();
	}

}