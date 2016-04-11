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

import org.w3c.dom.css.CSSRuleList;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.MediaListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

class MediaRuleConverter extends AbstractRuleConverter<CSSMediaRuleImpl, ComplexRule> {

    private final RuleConverter<CSSStyleRuleImpl, StyleRule> converter;

    public MediaRuleConverter() {
        this(new StyleRuleConverter());
    }

    MediaRuleConverter(final RuleConverter<CSSStyleRuleImpl, StyleRule> converter) {
        super(CSSMediaRuleImpl.class);
        this.converter = converter;
    }

    @Override
    protected ComplexRule convert2(final CSSMediaRuleImpl rule) {
        final List<String> selectors = new LinkedList<String>();
        final MediaListImpl mediaList = (MediaListImpl) rule.getMedia();
        for (int i = 0; i < mediaList.getLength(); ++i) {
            selectors.add(mediaList.mediaQuery(i).getCssText(null));
        }

        final List<StyleRule> rules = new LinkedList<StyleRule>();
        final CSSRuleList ruleList = rule.getCssRules();
        for (int i = 0; i < ruleList.getLength(); ++i) {
            rules.add(converter.convert(ruleList.item(i)));
        }

        return new ComplexRule("@media", selectors, rules);
    }
}
