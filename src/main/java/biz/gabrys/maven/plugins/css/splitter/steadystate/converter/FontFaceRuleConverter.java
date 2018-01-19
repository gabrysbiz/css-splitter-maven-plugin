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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

class FontFaceRuleConverter extends AbstractRuleConverter<CSSFontFaceRuleImpl, StyleRule> {

    private final StylePropertyConverter converter;

    FontFaceRuleConverter() {
        this(new StylePropertyConverter());
    }

    // for tests
    FontFaceRuleConverter(final StylePropertyConverter converter) {
        super(CSSFontFaceRuleImpl.class);
        this.converter = converter;
    }

    @Override
    protected StyleRule convert2(final CSSFontFaceRuleImpl rule) {
        final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
        final List<StyleProperty> properties = new LinkedList<StyleProperty>();
        for (final Property property : style.getProperties()) {
            properties.add(converter.convert(property));
        }
        return new StyleRule(Arrays.asList("@font-face"), properties, false);
    }
}
