/*
 * CSS Splitter Maven Plugin
 * https://gabrysbiz.github.io/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015-2020 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import com.steadystate.css.dom.CSSUnknownRuleImpl;
import com.steadystate.css.format.CSSFormatable;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

class UnknownRuleConverter extends AbstractRuleConverter<CSSUnknownRuleImpl, UnknownRule> {

    private final CssFormatter formatter;

    UnknownRuleConverter() {
        this(new CssFormatter());
    }

    // for tests
    UnknownRuleConverter(final CssFormatter formatter) {
        super(CSSUnknownRuleImpl.class);
        this.formatter = formatter;
    }

    @Override
    protected UnknownRule convert2(final CSSUnknownRuleImpl rule) {
        return new UnknownRule(formatter.format((CSSFormatable) rule));
    }
}
