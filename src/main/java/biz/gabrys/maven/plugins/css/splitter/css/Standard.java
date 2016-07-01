/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.css;

public enum Standard {

    VERSION_1_0("1.0"), VERSION_2_0("2.0"), VERSION_2_1("2.1"), VERSION_3_0("3.0");

    private final String version;

    Standard(final String version) {
        this.version = version;
    }

    public static Standard create(final String value) {
        for (final Standard standard : values()) {
            if (standard.version.equalsIgnoreCase(value)) {
                return standard;
            }
        }
        throw new IllegalArgumentException(String.format("CSS standard \"%s\" is not supported!", value));
    }
}
