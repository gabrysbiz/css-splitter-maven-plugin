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

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.counter.UnknownRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

final class UnknownRulePropertiesLimitValidator extends AbstractRulePropertiesLimitValidator<UnknownRule> {

    private final RuleCounter counter;

    UnknownRulePropertiesLimitValidator() {
        this(new UnknownRuleCounter());
    }

    // for tests
    UnknownRulePropertiesLimitValidator(final RuleCounter counter) {
        super(UnknownRule.class);
        this.counter = counter;
    }

    @Override
    protected void validate2(final UnknownRule rule, final int limit) throws ValidationException {
        final int value = counter.count(rule);
        if (value > limit) {
            throwException(value, limit, rule.getCode());
        }
    }
}
