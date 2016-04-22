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
import java.util.List;

import org.w3c.dom.css.CSSRule;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class AnyRuleConverter implements RuleConverter<NodeRule> {

    private final List<RuleConverter<?>> converters;

    AnyRuleConverter() {
        converters = new ArrayList<RuleConverter<?>>();
        converters.add(new StyleRuleConverter());
        converters.add(new MediaRuleConverter());
        converters.add(new FontFaceRuleConverter());
        converters.add(new PageRuleConverter());
        converters.add(new ImportRuleConverter());
        converters.add(new CharsetRuleConverter());
        converters.add(new UnknownRuleConverter());
    }

    // for tests
    AnyRuleConverter(final List<RuleConverter<?>> converters) {
        this.converters = new ArrayList<RuleConverter<?>>(converters);
    }

    public boolean isSupportedType(final CSSRule rule) {
        return getConverter(rule) != null;
    }

    public NodeRule convert(final CSSRule rule) {
        final RuleConverter<?> converter = getConverter(rule);
        if (converter != null) {
            return converter.convert(rule);
        }
        throw new UnsupportedRuleException(rule);
    }

    private RuleConverter<?> getConverter(final CSSRule rule) {
        for (final RuleConverter<?> converter : converters) {
            if (converter.isSupportedType(rule)) {
                return converter;
            }
        }
        return null;
    }
}
