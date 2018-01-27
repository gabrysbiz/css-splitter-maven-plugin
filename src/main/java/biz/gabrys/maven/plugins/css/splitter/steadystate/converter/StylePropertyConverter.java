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

import com.steadystate.css.dom.Property;
import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;

class StylePropertyConverter {

    StyleProperty convert(final Property property) {
        CSSFormatable cssFormatable = (CSSFormatable) property.getValue();
        CSSFormat cssFormat = new CSSFormat().setUseSourceStringValues(true);
        return new StyleProperty(property.getName(), cssFormatable.getCssText(cssFormat), property.isImportant());
    }
}
