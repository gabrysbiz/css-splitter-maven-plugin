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
package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import com.steadystate.css.dom.CSSPageRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

class PageRuleConverter extends AbstractRuleConverter<CSSPageRuleImpl, StyleRule> {

    PageRuleConverter() {
        super(CSSPageRuleImpl.class);
    }

    @Override
    protected StyleRule convert2(final CSSPageRuleImpl rule) {
        final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
        final List<StyleProperty> properties = new LinkedList<StyleProperty>();
        for (final Property property : style.getProperties()) {
            properties.add(new StyleProperty(property.getName(), property.getValue().getCssText()));
        }
        String selector = "@page";
        final String selectorText = rule.getSelectorText();
        if (StringUtils.isNotEmpty(selectorText)) {
            selector += ' ' + selectorText;
        }
        return new StyleRule(Arrays.asList(selector), properties);
    }
}
