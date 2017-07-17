package com.ulfric.rules.script;

import com.ulfric.rules.Trigger;

import java.util.function.Function;

public interface Script extends Function<Trigger, Object> {

}