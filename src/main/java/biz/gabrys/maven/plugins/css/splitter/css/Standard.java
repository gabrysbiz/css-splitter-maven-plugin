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

    public boolean isSameAs(final String value) {
        return version.equals(value);
    }

    @Override
    public String toString() {
        return version;
    }
}
