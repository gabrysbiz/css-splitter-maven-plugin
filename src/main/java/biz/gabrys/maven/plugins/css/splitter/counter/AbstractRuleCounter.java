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
package biz.gabrys.maven.plugins.css.splitter.counter;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRuleCounter<T extends NodeRule> implements RuleCounter {

    private final Class<T> clazz;

    protected AbstractRuleCounter(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public final boolean isSupportedType(final NodeRule rule) {
        return rule != null && rule.getClass() == clazz;
    }

    public final int count(final NodeRule rule) {
        if (!isSupportedType(rule)) {
            throw new UnsupportedRuleException(rule);
        }
        return count2(clazz.cast(rule));
    }

    protected abstract int count2(final T rule);
}
