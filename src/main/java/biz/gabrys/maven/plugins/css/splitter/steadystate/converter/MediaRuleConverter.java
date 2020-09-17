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

import org.w3c.dom.css.CSSRuleList;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.MediaListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class MediaRuleConverter extends AbstractRuleConverter<CSSMediaRuleImpl, ComplexRule> {

    private final RuleConverter<?> converter;
    private final CssFormatter formatter;

    MediaRuleConverter(final Standard standard, final boolean strict) {
        super(CSSMediaRuleImpl.class);
        converter = createConverter(this, standard, strict);
        formatter = new CssFormatter();
    }

    // for tests
    MediaRuleConverter(final RuleConverter<?> converter, final CssFormatter formatter) {
        super(CSSMediaRuleImpl.class);
        this.converter = converter;
        this.formatter = formatter;
    }

    static RuleConverter<?> createConverter(final MediaRuleConverter thisObject, final Standard standard, final boolean strict) {
        if (strict && Standard.VERSION_3_0 != standard) {
            return new StyleRuleConverter();
        }

        final List<RuleConverter<?>> converters = new ArrayList<RuleConverter<?>>();
        converters.add(new StyleRuleConverter());
        if (Standard.VERSION_3_0 == standard) {
            converters.add(thisObject);
        }
        if (!strict) {
            converters.add(new PageRuleConverter());
            converters.add(new UnknownRuleConverter());
        }
        return new MultipleRuleConverter(converters);
    }

    @Override
    protected ComplexRule convert2(final CSSMediaRuleImpl rule) {
        final List<String> selectors = new LinkedList<String>();
        final MediaListImpl mediaList = (MediaListImpl) rule.getMedia();
        for (int i = 0; i < mediaList.getLength(); ++i) {
            selectors.add(formatter.format(mediaList.mediaQuery(i)));
        }

        final List<NodeRule> rules = new LinkedList<NodeRule>();
        final CSSRuleList ruleList = rule.getCssRules();
        for (int i = 0; i < ruleList.getLength(); ++i) {
            rules.add(converter.convert(ruleList.item(i)));
        }

        return new ComplexRule("@media", selectors, rules);
    }
}
