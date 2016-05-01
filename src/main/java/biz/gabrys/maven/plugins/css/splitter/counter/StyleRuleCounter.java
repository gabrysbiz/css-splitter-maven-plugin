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
package biz.gabrys.maven.plugins.css.splitter.counter;

import java.util.Collection;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public class StyleRuleCounter extends AbstractRuleCounter<StyleRule> {

    public StyleRuleCounter() {
        super(StyleRule.class);
    }

    @Override
    protected int count2(final StyleRule rule) {
        final Collection<StyleProperty> properties = rule.getProperties();
        return properties.isEmpty() ? 1 : properties.size();
    }
}
