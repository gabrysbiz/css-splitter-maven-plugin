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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

class FontFaceRuleConverter extends AbstractRuleConverter<CSSFontFaceRuleImpl, StyleRule> {

    public FontFaceRuleConverter() {
        super(CSSFontFaceRuleImpl.class);
    }

    @Override
    protected StyleRule convert2(final CSSFontFaceRuleImpl rule) {
        final CSSStyleDeclarationImpl style = (CSSStyleDeclarationImpl) rule.getStyle();
        final List<StyleProperty> properties = new LinkedList<StyleProperty>();
        for (final Property property : style.getProperties()) {
            properties.add(new StyleProperty(property.getName(), property.getValue().getCssText()));
        }
        return new StyleRule(Arrays.asList("@font-face"), properties, false);
    }
}
