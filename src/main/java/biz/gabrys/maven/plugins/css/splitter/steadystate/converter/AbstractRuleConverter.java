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

import org.w3c.dom.css.CSSRule;

import com.steadystate.css.dom.AbstractCSSRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

abstract class AbstractRuleConverter<F extends AbstractCSSRuleImpl, T extends NodeRule> implements RuleConverter<T> {

    private final Class<F> clazz;

    protected AbstractRuleConverter(final Class<F> clazz) {
        this.clazz = clazz;
    }

    @Override
    public final boolean isSupportedType(final CSSRule rule) {
        return rule != null && rule.getClass() == clazz;
    }

    @Override
    public final T convert(final CSSRule rule) {
        if (!isSupportedType(rule)) {
            throw new UnsupportedRuleException(rule);
        }
        return convert2(clazz.cast(rule));
    }

    protected abstract T convert2(F rule);
}
