package com.ulfric.rules;

import com.ulfric.commons.value.Bean;
import com.ulfric.rules.consequence.Consequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RuleList {

	private final List<Entry> rules = new ArrayList<>();

	public void add(Entry entry) {
		Objects.requireNonNull(entry, "entry");

		rules.add(entry);
	}

	public Result apply(Trigger trigger) {
		for (Entry entry : rules) {
			for (Rule rule : entry.rules) {
				Result result = rule.test(trigger);

				if (result == Result.EXPLICITLY_ALLOWED) {
					return Result.EXPLICITLY_ALLOWED;
				}

				if (result == Result.FAILED) {
					entry.consequences.forEach(consequence -> consequence.apply(trigger));
					return Result.FAILED;
				}
			}
		}

		return Result.PASSED;
	}

	public static final class Entry extends Bean {

		public static Builder builder() {
			return new Builder();
		}

		private final List<Rule> rules;
		private final List<Consequence> consequences;

		Entry(List<Rule> rules, List<Consequence> consequences) {
			this.rules = rules;
			this.consequences = consequences;
		}

		public static final class Builder implements org.apache.commons.lang3.builder.Builder<Entry> {
			private final List<Rule> rules = new ArrayList<>();
			private final List<Consequence> consequences = new ArrayList<>();

			Builder() {
			}

			@Override
			public Entry build() {
				if (rules.isEmpty()) {
					throw new IllegalStateException("No rules specified in entry");
				}

				return new Entry(new ArrayList<>(rules), new ArrayList<>(consequences));
			}

			public Builder addRule(Rule rule) {
				Objects.requireNonNull(rule, "rule");

				rules.add(rule);
				return this;
			}

			public Builder addConsequence(Consequence consequence) {
				Objects.requireNonNull(consequence, "consequence");

				consequences.add(consequence);
				return this;
			}
		}
	}

}