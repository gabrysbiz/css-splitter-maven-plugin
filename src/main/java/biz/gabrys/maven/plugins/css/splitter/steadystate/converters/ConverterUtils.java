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

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

final class ConverterUtils {

    private ConverterUtils() {
        // blocks the possibility of create a new instance
    }

    static NodeRule convert(final CSSRule rule, final Iterable<RuleConverter<?, ?>> converters) {
        for (final RuleConverter<?, ?> converter : converters) {
            if (converter.getSupportedType() == rule.getClass()) {
                return converter.convert(rule);
            }
        }
        throw new UnsupportedRuleException(rule);
    }
}
