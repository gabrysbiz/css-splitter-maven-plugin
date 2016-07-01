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
package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StylePropertiesLimitValidator {

    private final List<RulePropertiesLimitValidator> validators;
    private final int limit;

    public StylePropertiesLimitValidator(final int limit) {
        this(Arrays.<RulePropertiesLimitValidator>asList(new StyleRulePropertiesLimitValidator(), new ComplexRulePropertiesLimitValidator(),
                new UnknownRulePropertiesLimitValidator()), limit);
    }

    // for tests
    StylePropertiesLimitValidator(final List<RulePropertiesLimitValidator> validators, final int limit) {
        this.validators = new ArrayList<RulePropertiesLimitValidator>(validators);
        this.limit = limit;
    }

    public void validate(final StyleSheet stylesheet) {
        for (final NodeRule rule : stylesheet.getRules()) {
            validate(rule);
        }
    }

    private void validate(final NodeRule rule) {
        for (final RulePropertiesLimitValidator validator : validators) {
            if (validator.isSupportedType(rule)) {
                validator.validate(rule, limit);
                return;
            }
        }
    }
}
