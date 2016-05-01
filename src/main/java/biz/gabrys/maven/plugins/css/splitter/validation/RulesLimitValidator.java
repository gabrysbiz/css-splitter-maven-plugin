/**
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.validation;

import biz.gabrys.maven.plugins.css.splitter.counter.AnyRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class RulesLimitValidator {

    private final RuleCounter counter;
    private final int limit;

    public RulesLimitValidator(final int limit) {
        this(limit, new AnyRuleCounter());
    }

    // for tests
    RulesLimitValidator(final int limit, final RuleCounter counter) {
        this.limit = limit;
        this.counter = counter;
    }

    public void validate(final StyleSheet stylesheet) throws ValidationException {
        final int value = count(stylesheet);
        if (value > limit) {
            throw new ValidationException(String.format("The number of style rules (%d) exceeded the allowable limit (%d)!", value, limit));
        }
    }

    int count(final StyleSheet stylesheet) {
        int value = 0;
        for (final NodeRule rule : stylesheet.getRules()) {
            value += counter.count(rule);
        }
        return value;
    }
}
