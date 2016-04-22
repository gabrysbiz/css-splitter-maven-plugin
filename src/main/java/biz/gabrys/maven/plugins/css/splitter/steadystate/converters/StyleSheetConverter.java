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

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public class StyleSheetConverter {

    private final RuleConverter<?> converter;

    public StyleSheetConverter() {
        this(new AnyRuleConverter());
    }

    // for tests
    StyleSheetConverter(final RuleConverter<?> converter) {
        this.converter = converter;
    }

    public StyleSheet convert(final CSSStyleSheet stylesheet) {
        final List<NodeRule> rules = new LinkedList<NodeRule>();
        final CSSRuleList ruleList = stylesheet.getCssRules();
        for (int i = 0; i < ruleList.getLength(); ++i) {
            final CSSRule rule = ruleList.item(i);
            if (converter.isSupportedType(rule)) {
                rules.add(converter.convert(rule));
            } else {
                throw new UnsupportedRuleException(rule);
            }
        }
        return new StyleSheet(rules);
    }
}
