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

import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.TreeSplitUtils;

//TODO add tests
class StyleRuleSplitter extends AbstractRuleSplitter<StyleRule> {

    StyleRuleSplitter() {
        super(StyleRule.class);
    }

    @Override
    protected SplitResult<StyleRule> split2(final StyleRule rule, final int splitAfter) {
        if (!rule.isSplittable()) {
            return new SplitResult<StyleRule>(null, rule);
        }

        final List<StyleProperty> properties = rule.getProperties();
        final StyleRule first = new StyleRule(rule.getSelectors(), properties.subList(0, splitAfter));
        final StyleRule second = new StyleRule(rule.getSelectors(), properties.subList(splitAfter, properties.size()));
        TreeSplitUtils.fillNeighbors(rule, first, second);
        return new SplitResult<StyleRule>(first, second);
    }
}
