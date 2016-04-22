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

public class UnsupportedRuleException extends RuntimeException {

    private static final long serialVersionUID = -1784456425651473954L;

    public UnsupportedRuleException(final CSSRule rule) {
        super(String.format("Rule represented by \"%s\" class (type: %d) is unsupported in current contex! CSS code that causes error:%n%s",
                rule.getClass().getName(), rule.getType(), rule.getCssText()));
    }
}
