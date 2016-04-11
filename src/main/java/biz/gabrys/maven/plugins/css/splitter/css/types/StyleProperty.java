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
package biz.gabrys.maven.plugins.css.splitter.css.types;

public class StyleProperty extends Node<StyleRule, StyleProperty> {

    private final String name;
    private final String value;

    public StyleProperty(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + ": " + value + ';';
    }
}
