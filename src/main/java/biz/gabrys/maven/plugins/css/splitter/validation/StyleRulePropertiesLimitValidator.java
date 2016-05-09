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

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

final class StyleRulePropertiesLimitValidator extends AbstractRulePropertiesLimitValidator<StyleRule> {

    StyleRulePropertiesLimitValidator() {
        super(StyleRule.class);
    }

    @Override
    protected void validate2(final StyleRule rule, final int limit) {
        if (rule.isSplittable()) {
            return;
        }
        final int size = rule.getSize();
        if (size > limit) {
            throwException(size, limit, rule.getCode());
        }
    }
}
