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
package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.ArrayList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class MultipleRuleSplitter implements RuleSplitter {

    private final List<RuleSplitter> splitters;

    MultipleRuleSplitter(final List<RuleSplitter> splitters) {
        this.splitters = new ArrayList<RuleSplitter>(splitters);
    }

    public boolean isSplittable(final NodeRule rule) {
        for (final RuleSplitter splitter : splitters) {
            if (splitter.isSplittable(rule)) {
                return true;
            }
        }
        return false;
    }

    public SplitResult split(final NodeRule rule, final int splitAfter) {
        for (final RuleSplitter splitter : splitters) {
            if (splitter.isSplittable(rule)) {
                return splitter.split(rule, splitAfter);
            }
        }
        throw new IllegalArgumentException(String.format("The rule is unsplittable! Code:%n%s", rule));
    }
}
