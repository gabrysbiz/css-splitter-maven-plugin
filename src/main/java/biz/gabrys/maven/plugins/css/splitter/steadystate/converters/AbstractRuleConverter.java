/**
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

import org.w3c.dom.css.CSSRule;

import com.steadystate.css.dom.AbstractCSSRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRuleConverter<F extends AbstractCSSRuleImpl, T extends NodeRule> implements RuleConverter<T> {

    private final Class<F> clazz;

    protected AbstractRuleConverter(final Class<F> clazz) {
        this.clazz = clazz;
    }

    public final boolean isSupportedType(final CSSRule rule) {
        return rule != null && rule.getClass() == clazz;
    }

    public final T convert(final CSSRule rule) {
        if (!isSupportedType(rule)) {
            throw new UnsupportedRuleException(rule);
        }
        return convert2(clazz.cast(rule));
    }

    protected abstract T convert2(F rule);
}
