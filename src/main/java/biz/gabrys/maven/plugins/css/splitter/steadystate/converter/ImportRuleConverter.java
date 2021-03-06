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
package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import com.steadystate.css.dom.CSSImportRuleImpl;
import com.steadystate.css.format.CSSFormatable;

import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;

class ImportRuleConverter extends AbstractRuleConverter<CSSImportRuleImpl, SimpleRule> {

    private final CssFormatter formatter;

    ImportRuleConverter() {
        this(new CssFormatter());
    }

    // for tests
    ImportRuleConverter(final CssFormatter formatter) {
        super(CSSImportRuleImpl.class);
        this.formatter = formatter;
    }

    @Override
    protected SimpleRule convert2(final CSSImportRuleImpl rule) {
        return new SimpleRule(formatter.format((CSSFormatable) rule));
    }
}
