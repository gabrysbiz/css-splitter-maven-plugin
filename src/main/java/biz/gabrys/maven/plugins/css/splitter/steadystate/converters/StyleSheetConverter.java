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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public class StyleSheetConverter {

    private final List<RuleConverter<?, ?>> converters;

    public StyleSheetConverter() {
        converters = new ArrayList<RuleConverter<?, ?>>();
        converters.add(new StyleRuleConverter());
        converters.add(new MediaRuleConverter());
        converters.add(new FontFaceRuleConverter());
        converters.add(new PageRuleConverter());
        converters.add(new ImportRuleConverter());
        converters.add(new CharsetRuleConverter());
        converters.add(new UnknownRuleConverter());
    }

    StyleSheetConverter(final List<RuleConverter<?, ?>> converters) {
        this.converters = new ArrayList<RuleConverter<?, ?>>(converters);
    }

    public StyleSheet convert(final CSSStyleSheet stylesheet) {
        final List<NodeRule> rules = new LinkedList<NodeRule>();
        final CSSRuleList ruleList = stylesheet.getCssRules();
        for (int i = 0; i < ruleList.getLength(); ++i) {
            rules.add(ConverterUtils.convert(ruleList.item(i), converters));
        }
        return new StyleSheet(rules);
    }
}
