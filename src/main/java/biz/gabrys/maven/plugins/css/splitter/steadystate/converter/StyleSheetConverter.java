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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public class StyleSheetConverter {

    private final RuleConverter<?> converter;

    public StyleSheetConverter(final Standard standard, final boolean strict) {
        this(createConverter(standard, strict));
    }

    // for tests
    StyleSheetConverter(final RuleConverter<?> converter) {
        this.converter = converter;
    }

    static MultipleRuleConverter createConverter(final Standard standard, final boolean strict) {
        final List<RuleConverter<?>> converters = new ArrayList<RuleConverter<?>>();
        converters.add(new StyleRuleConverter());
        converters.add(new MediaRuleConverter(standard, strict));
        converters.add(new FontFaceRuleConverter());
        converters.add(new PageRuleConverter());
        converters.add(new ImportRuleConverter());
        converters.add(new CharsetRuleConverter());
        converters.add(new UnknownRuleConverter());
        return new MultipleRuleConverter(converters);
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
