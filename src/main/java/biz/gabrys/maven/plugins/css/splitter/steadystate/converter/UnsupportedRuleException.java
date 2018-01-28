/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import org.w3c.dom.css.CSSRule;

public class UnsupportedRuleException extends RuntimeException {

    private static final long serialVersionUID = -1784456425651473954L;

    UnsupportedRuleException(final CSSRule rule) {
        super(createMessage(rule, new CssFormatter()));
    }

    static String createMessage(final CSSRule rule, final CssFormatter formatter) {
        return String.format(
                "Rule represented by \"%s\" class (type: %d) is unsupported in current contex! CSS code that causes error:%n%s",
                rule.getClass().getName(), rule.getType(), formatter.format(rule));
    }
}
