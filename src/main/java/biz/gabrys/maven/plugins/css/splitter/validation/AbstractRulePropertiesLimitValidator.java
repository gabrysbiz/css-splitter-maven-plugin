/**
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
package biz.gabrys.maven.plugins.css.splitter.validation;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

abstract class AbstractRulePropertiesLimitValidator<T extends NodeRule> implements RulePropertiesLimitValidator {

    private final Class<T> clazz;

    protected AbstractRulePropertiesLimitValidator(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public final boolean isSupportedType(final NodeRule rule) {
        return rule != null && rule.getClass() == clazz;
    }

    public final void validate(final NodeRule rule, final int limit) throws ValidationException {
        if (!isSupportedType(rule)) {
            throw new IllegalArgumentException(String.format("Cannot cast an instance of the %s to the %s class!",
                    rule == null ? "null" : rule.getClass().getName(), clazz.getName()));
        }
        validate2(clazz.cast(rule), limit);
    }

    protected abstract void validate2(T rule, int limit) throws ValidationException;

    protected void throwException(final int current, final int limit, final String code) throws ValidationException {
        throw new ValidationException(
                String.format("The number of style properties (%d) in non-splittable rule exceeded the allowable limit (%d)!"
                        + " CSS code that causes validation error:%n%s", current, limit, code));
    }
}
