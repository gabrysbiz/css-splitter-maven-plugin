/*
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
package biz.gabrys.maven.plugins.css.splitter.css.counter;

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
