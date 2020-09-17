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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import com.steadystate.css.dom.CSSPageRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

class PageRuleConverter extends AbstractRuleConverter<CSSPageRuleImpl, StyleRule> {

    private final StylePropertyConverter converter;

    PageRuleConverter() {
        this(new StylePropertyConverter());
    }

    // for tests
    PageRuleConverter(final StylePropertyConverter converter) {
        super(CSSPageRuleImpl.class);
        this.converter = converter;
    }

    @Override
    protected StyleRule convert2(final CSSPageRuleImpl rule) {
        final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
        final List<StyleProperty> properties = new LinkedList<StyleProperty>();
        for (final Property property : style.getProperties()) {
            properties.add(converter.convert(property));
        }
        String selector = "@page";
        final String selectorText = rule.getSelectorText();
        if (StringUtils.isNotEmpty(selectorText)) {
            selector += ' ' + selectorText;
        }
        return new StyleRule(Arrays.asList(selector), properties);
    }
}
