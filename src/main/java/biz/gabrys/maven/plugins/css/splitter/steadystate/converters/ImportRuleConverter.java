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
package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import com.steadystate.css.dom.CSSImportRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;

class ImportRuleConverter extends AbstractRuleConverter<CSSImportRuleImpl, SimpleRule> {

    public ImportRuleConverter() {
        super(CSSImportRuleImpl.class);
    }

    @Override
    protected SimpleRule convert2(final CSSImportRuleImpl rule) {
        return new SimpleRule(rule.getCssText());
    }
}
