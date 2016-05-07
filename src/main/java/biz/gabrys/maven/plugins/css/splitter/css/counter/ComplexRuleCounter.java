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

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public class ComplexRuleCounter extends AbstractRuleCounter<ComplexRule> {

    private final RuleCounter styleCounter;

    public ComplexRuleCounter() {
        this(new StyleRuleCounter());
    }

    ComplexRuleCounter(final RuleCounter styleCounter) {
        super(ComplexRule.class);
        this.styleCounter = styleCounter;
    }

    @Override
    protected int count2(final ComplexRule rule) {
        final Iterable<StyleRule> styles = rule.getRules();
        int value = 0;
        for (final StyleRule child : styles) {
            value += styleCounter.count(child);
        }
        return value;
    }
}
