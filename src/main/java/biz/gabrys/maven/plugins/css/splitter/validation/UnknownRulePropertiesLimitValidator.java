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

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.counter.UnknownRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

final class UnknownRulePropertiesLimitValidator extends AbstractRulePropertiesLimitValidator<UnknownRule> {

    private final RuleCounter counter;
    private final Log logger;

    UnknownRulePropertiesLimitValidator(final Log logger) {
        this(new UnknownRuleCounter(), logger);
    }

    // for tests
    UnknownRulePropertiesLimitValidator(final RuleCounter counter, final Log logger) {
        super(UnknownRule.class);
        this.counter = counter;
        this.logger = logger;
    }

    @Override
    protected void validate2(final UnknownRule rule, final int limit) throws ValidationException {
        if (logger.isDebugEnabled()) {
            logger.debug("Found non-standard (unknown) rule:\n" + rule.getCode());
        }
        final int value = counter.count(rule);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("I guess it contains %d propert%s.", value, value != 1 ? "ies" : "y"));
        }
        if (value > limit) {
            throwException(value, limit, rule.getCode());
        }
    }
}
