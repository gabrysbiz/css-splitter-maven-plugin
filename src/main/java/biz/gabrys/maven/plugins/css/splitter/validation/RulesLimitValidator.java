/*
 * CSS Splitter Maven Plugin
 * http://www.gabrys.biz/projects/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *      https://raw.githubusercontent.com/gabrysbiz/css-splitter-maven-plugin/master/src/main/resources/license.txt
 */
package biz.gabrys.maven.plugins.css.splitter.validation;

import biz.gabrys.maven.plugins.css.splitter.counter.AnyRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

//TODO add tests
public final class RulesLimitValidator {

    private final AnyRuleCounter counter;
    private final int limit;

    public RulesLimitValidator(final int limit) {
        this.limit = limit;
        counter = new AnyRuleCounter();
    }

    public void validate(final StyleSheet stylesheet) throws ValidationException {
        final int value = count(stylesheet);
        if (value > limit) {
            throw new ValidationException(String.format("The number of style rules (%d) exceeded the allowable limit (%d).", value, limit));
        }
    }

    private int count(final StyleSheet stylesheet) {
        int value = 0;
        for (final NodeRule rule : stylesheet.getRules()) {
            value += counter.count(rule);
        }
        return value;
    }
}
