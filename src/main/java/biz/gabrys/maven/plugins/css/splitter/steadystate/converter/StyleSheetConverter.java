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
package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public class StyleSheetConverter {

    private final RuleConverter<?> converter;

    public StyleSheetConverter(final boolean strict) {
        final List<RuleConverter<?>> converters = new ArrayList<RuleConverter<?>>();
        converters.add(new StyleRuleConverter());
        converters.add(new MediaRuleConverter(strict));
        converters.add(new FontFaceRuleConverter());
        converters.add(new PageRuleConverter());
        converters.add(new ImportRuleConverter());
        converters.add(new CharsetRuleConverter());
        converters.add(new UnknownRuleConverter());
        converter = new MultipleRuleConverter(converters);
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
