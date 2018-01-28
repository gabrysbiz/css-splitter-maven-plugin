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
package biz.gabrys.maven.plugins.css.splitter.token;

public enum TokenType {

    CUSTOM, DATE, NONE;

    public static TokenType create(final String value) {
        for (final TokenType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("Token type \"%s\" is not supported!", value));
    }

    public TokenValueFactory createFactory() {
        switch (this) {
            case CUSTOM:
                return new CustomValueTokenFactory();
            case DATE:
                return new DateTokenValueFactory();
            default:
                throw new UnsupportedOperationException(
                        String.format("Cannot create %s for \"%s\" toke type!", TokenValueFactory.class.getSimpleName(), name()));
        }
    }
}
