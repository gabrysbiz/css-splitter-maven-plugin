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

import java.util.ArrayList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class AnyRuleSplitter implements RuleSplitter<NodeRule> {

    private final List<RuleSplitter<? extends NodeRule>> splitters = new ArrayList<RuleSplitter<? extends NodeRule>>();

    AnyRuleSplitter() {
        splitters.add(new ComplexRuleSplitter());
        splitters.add(new StyleRuleSplitter());
    }

    // for tests
    AnyRuleSplitter(final List<RuleSplitter<? extends NodeRule>> splitters) {
        this.splitters.addAll(splitters);
    }

    public boolean isSplittable(final NodeRule rule) {
        for (final RuleSplitter<? extends NodeRule> splitter : splitters) {
            if (splitter.isSplittable(rule)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public SplitResult<NodeRule> split(final NodeRule rule, final int splitAfter) {
        for (final RuleSplitter<? extends NodeRule> splitter : splitters) {
            if (splitter.isSplittable(rule)) {
                return (SplitResult<NodeRule>) splitter.split(rule, splitAfter);
            }
        }
        throw new IllegalArgumentException(String.format("The rule is unsplittable! Code:%n%s", rule));
    }
}
