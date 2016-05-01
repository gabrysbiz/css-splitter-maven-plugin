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
package biz.gabrys.maven.plugins.css.splitter.split;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRuleSplitter<T extends NodeRule> implements RuleSplitter<T> {

    private final Class<T> clazz;

    protected AbstractRuleSplitter(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public final boolean isSplittable(final NodeRule rule) {
        if (rule == null || rule.getClass() != clazz) {
            return false;
        }
        return isSplittable2(clazz.cast(rule));
    }

    protected abstract boolean isSplittable2(T rule);

    public final SplitResult<T> split(final NodeRule rule, final int splitAfter) {
        if (!isSplittable(rule)) {
            throw new IllegalArgumentException(String.format("The rule is unsplittable! Code:%n%s", rule));
        }
        return split2(clazz.cast(rule), splitAfter);
    }

    protected abstract SplitResult<T> split2(T rule, int splitAfter);
}
