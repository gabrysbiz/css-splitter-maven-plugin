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
package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import com.steadystate.css.dom.CSSCharsetRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;

class CharsetRuleConverter extends AbstractRuleConverter<CSSCharsetRuleImpl, SimpleRule> {

    CharsetRuleConverter() {
        super(CSSCharsetRuleImpl.class);
    }

    @Override
    protected SimpleRule convert2(final CSSCharsetRuleImpl rule) {
        return new SimpleRule(rule.getCssText());
    }
}
