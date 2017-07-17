package com.ulfric.rules.security;

import com.ulfric.rules.RuleEngine;
import com.ulfric.rules.UncompiledRule;

import java.util.function.BiPredicate;

public interface SecurityScanner extends BiPredicate<RuleEngine, UncompiledRule> {

}