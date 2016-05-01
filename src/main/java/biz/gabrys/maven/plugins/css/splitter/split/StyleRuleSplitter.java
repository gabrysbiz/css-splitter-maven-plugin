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

import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

class StyleRuleSplitter extends AbstractRuleSplitter<StyleRule> {

    private final NeighborsManager neighborsManager;

    StyleRuleSplitter() {
        this(new NeighborsManager());
    }

    // fot tests
    StyleRuleSplitter(final NeighborsManager neighborsManager) {
        super(StyleRule.class);
        this.neighborsManager = neighborsManager;
    }

    @Override
    protected boolean isSplittable2(final StyleRule rule) {
        return rule.isSplittable();
    }

    @Override
    protected SplitResult<StyleRule> split2(final StyleRule rule, final int splitAfter) {
        final List<StyleProperty> properties = rule.getProperties();
        final StyleRule first = new StyleRule(rule.getSelectors(), properties.subList(0, splitAfter));
        final StyleRule second = new StyleRule(rule.getSelectors(), properties.subList(splitAfter, properties.size()));
        neighborsManager.fill(rule, first, second);
        return new SplitResult<StyleRule>(first, second);
    }
}
