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
package biz.gabrys.maven.plugins.css.splitter.validation;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class RulesLimitValidator {

    private final int limit;

    public RulesLimitValidator(final int limit) {
        this.limit = limit;
    }

    public void validate(final StyleSheet stylesheet) {
        final int size = stylesheet.getSize();
        if (size > limit) {
            throw new ValidationException(String.format("The number of style rules (%d) exceeded the allowable limit (%d)!", size, limit));
        }
    }
}
