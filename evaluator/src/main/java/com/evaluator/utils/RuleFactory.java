package com.evaluator.utils;

import com.evaluator.models.Rule;

/**
 * Abstract factory for creating rule
 */
public interface RuleFactory {

    /**
     * @return Rule created by that abstract factory
     */
    Rule createRule();
}
