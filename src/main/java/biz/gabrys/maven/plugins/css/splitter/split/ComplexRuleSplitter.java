/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.split;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;

class ComplexRuleSplitter extends AbstractRuleSplitter<ComplexRule> {

    private final RulesSplitter splitter;
    private final NeighborsManager neighborsManager;

    ComplexRuleSplitter() {
        this(new RulesSplitter(new StyleRuleSplitter()), new NeighborsManager());
    }

    // for tests
    ComplexRuleSplitter(final RulesSplitter splitter, final NeighborsManager neighborsManager) {
        super(ComplexRule.class);
        this.splitter = splitter;
        this.neighborsManager = neighborsManager;
    }

    @Override
    protected boolean isSplittable2(final ComplexRule rule) {
        return true;
    }

    @Override
    protected SplitResult split2(final ComplexRule rule, final int splitAfter) {
        final RulesContainer container = splitter.split(rule.getRules(), splitAfter);
        final ComplexRule first = new ComplexRule(rule.getType(), rule.getSelectors(), container.before);
        final ComplexRule second = new ComplexRule(rule.getType(), rule.getSelectors(), container.after);
        neighborsManager.fill(rule, first, second);
        return new SplitResult(first, second);
    }
}
