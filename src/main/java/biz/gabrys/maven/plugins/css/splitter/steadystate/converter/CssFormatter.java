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

import org.w3c.css.sac.Selector;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

class CssFormatter {

    String format(final CSSRule rule) {
        if (rule instanceof CSSFormatable) {
            return format((CSSFormatable) rule);
        }
        return rule.getCssText();
    }

    String format(final Selector selector) {
        if (selector instanceof CSSFormatable) {
            return format((CSSFormatable) selector);
        }
        return selector.toString();
    }

    String format(final CSSValue value) {
        if (value instanceof CSSFormatable) {
            return format((CSSFormatable) value);
        }
        return value.getCssText();
    }

    String format(final CSSFormatable formatable) {
        return formatable.getCssText(new CSSFormat().setUseSourceStringValues(true).setRgbAsHex(true));
    }
}
