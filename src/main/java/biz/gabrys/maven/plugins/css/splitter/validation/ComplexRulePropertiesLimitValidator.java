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

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

final class ComplexRulePropertiesLimitValidator extends AbstractRulePropertiesLimitValidator<ComplexRule> {

    private final List<RulePropertiesLimitValidator> validators;

    ComplexRulePropertiesLimitValidator() {
        this(Arrays.<RulePropertiesLimitValidator>asList(new StyleRulePropertiesLimitValidator(),
                new UnknownRulePropertiesLimitValidator()));
    }

    // for tests
    ComplexRulePropertiesLimitValidator(final List<RulePropertiesLimitValidator> validators) {
        super(ComplexRule.class);
        this.validators = new ArrayList<RulePropertiesLimitValidator>(validators);
    }

    @Override
    protected void validate2(final ComplexRule rule, final int limit) {
        for (final NodeRule child : rule.getRules()) {
            for (final RulePropertiesLimitValidator validator : validators) {
                if (validator.isSupportedType(child)) {
                    validator.validate(child, limit);
                }
            }
        }
    }
}
