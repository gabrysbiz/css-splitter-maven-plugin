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

import org.w3c.dom.css.CSSRule;

import com.steadystate.css.dom.AbstractCSSRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRuleConverter<F extends AbstractCSSRuleImpl, T extends NodeRule> implements RuleConverter<F, T> {

    private final Class<F> clazz;

    protected AbstractRuleConverter(final Class<F> clazz) {
        this.clazz = clazz;
    }

    public final Class<F> getSupportedType() {
        return clazz;
    }

    public final T convert(final CSSRule rule) {
        if (rule.getClass() != clazz) {
            throw new UnsupportedRuleException(rule);
        }
        return convert2(clazz.cast(rule));
    }

    protected abstract T convert2(F rule);
}
