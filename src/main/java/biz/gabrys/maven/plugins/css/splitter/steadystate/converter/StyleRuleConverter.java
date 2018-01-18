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

import java.util.LinkedList;
import java.util.List;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.parser.SelectorListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

class StyleRuleConverter extends AbstractRuleConverter<CSSStyleRuleImpl, StyleRule> {

    StyleRuleConverter() {
        super(CSSStyleRuleImpl.class);
    }

    @Override
    protected StyleRule convert2(final CSSStyleRuleImpl rule) {
        final List<String> selectors = new LinkedList<String>();
        final SelectorListImpl selectorsList = (SelectorListImpl) rule.getSelectors();
        for (int i = 0; i < selectorsList.getLength(); ++i) {
            selectors.add(selectorsList.item(i).toString());
        }
        final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
        final List<StyleProperty> properties = new LinkedList<StyleProperty>();
        for (final Property property : style.getProperties()) {
            properties.add(new StyleProperty(property.getName(), property.getValue().getCssText()));
        }
        return new StyleRule(selectors, properties);
    }
}
