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

import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;

class StylePropertyConverter {

    private final CssFormatter formatter;

    StylePropertyConverter() {
        this(new CssFormatter());
    }

    // for tests
    StylePropertyConverter(final CssFormatter formatter) {
        this.formatter = formatter;
    }

    StyleProperty convert(final Property property) {
        return new StyleProperty(property.getName(), formatter.format(property.getValue()), property.isImportant());
    }
}
