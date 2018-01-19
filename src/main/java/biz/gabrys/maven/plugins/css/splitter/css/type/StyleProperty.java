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
package biz.gabrys.maven.plugins.css.splitter.css.type;

public class StyleProperty extends NodeImpl<StyleRule, StyleProperty> {

    private final String name;
    private final String value;
    private final boolean important;

    public StyleProperty(final String name, final String value, final boolean important) {
        this.name = name;
        this.value = value;
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isImportant() {
        return important;
    }

    public String getCode() {
        final StringBuilder code = new StringBuilder();
        code.append(name);
        code.append(": ");
        code.append(value);
        if (important) {
            code.append(" !important");
        }
        code.append(';');
        return code.toString();
    }

    @Override
    public String toString() {
        return getCode() + '\n';
    }
}
