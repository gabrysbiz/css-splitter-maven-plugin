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
package biz.gabrys.maven.plugins.css.splitter.split;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRuleSplitter<T extends NodeRule> implements RuleSplitter<T> {

    private final Class<T> clazz;

    protected AbstractRuleSplitter(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getSupportedType() {
        return clazz;
    }

    public final SplitResult<T> split(final NodeRule rule, final int splitAfter) {
        if (rule.getClass() != clazz) {
            throw new IllegalArgumentException(
                    String.format("Cannot cast an instance of the %s to the %s class.", rule.getClass().getName(), clazz.getName()));
        }
        return split2(clazz.cast(rule), splitAfter);
    }

    protected abstract SplitResult<T> split2(T rule, int splitAfter);
}
