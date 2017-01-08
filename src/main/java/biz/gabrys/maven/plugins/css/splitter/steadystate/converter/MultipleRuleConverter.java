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
import java.util.Collection;
import java.util.List;

import org.w3c.dom.css.CSSRule;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class MultipleRuleConverter implements RuleConverter<NodeRule> {

    protected final List<RuleConverter<?>> converters;

    MultipleRuleConverter(final Collection<RuleConverter<?>> converters) {
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
