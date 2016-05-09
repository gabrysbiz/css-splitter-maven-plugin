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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.css.CSSRuleList;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.MediaListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class MediaRuleConverter extends AbstractRuleConverter<CSSMediaRuleImpl, ComplexRule> {

    private final RuleConverter<?> converter;

    MediaRuleConverter(final boolean strict) {
        super(CSSMediaRuleImpl.class);
        if (strict) {
            converter = new StyleRuleConverter();
        } else {
            final List<RuleConverter<?>> converters = new ArrayList<RuleConverter<?>>();
            converters.add(new StyleRuleConverter());
            converters.add(new PageRuleConverter());
            converters.add(new UnknownRuleConverter());
            converter = new MultipleRuleConverter(converters);
        }
    }

    // for tests
    MediaRuleConverter(final RuleConverter<?> converter) {
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

        final List<NodeRule> rules = new LinkedList<NodeRule>();
        final CSSRuleList ruleList = rule.getCssRules();
        for (int i = 0; i < ruleList.getLength(); ++i) {
            rules.add(converter.convert(ruleList.item(i)));
        }

        return new ComplexRule("@media", selectors, rules);
    }
}
