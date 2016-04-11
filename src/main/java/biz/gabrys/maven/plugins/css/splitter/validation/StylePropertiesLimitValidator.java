/*
 * CSS Splitter Maven Plugin
 * http://www.gabrys.biz/projects/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *      https://raw.githubusercontent.com/gabrysbiz/css-splitter-maven-plugin/master/src/main/resources/license.txt
 */
package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

// TODO add tests
public class StylePropertiesLimitValidator {

    private final Map<Class<?>, RulePropertiesLimitValidator> validators = new ConcurrentHashMap<Class<?>, RulePropertiesLimitValidator>();
    private final int limit;

    public StylePropertiesLimitValidator(final int limit) {
        this.limit = limit;
        addValidator(new StyleRulePropertiesLimitValidator());
        addValidator(new ComplexRulePropertiesLimitValidator());
        addValidator(new UnknownRulePropertiesLimitValidator());
    }

    private void addValidator(final RulePropertiesLimitValidator validator) {
        validators.put(validator.getSupportedType(), validator);
    }

    public void validate(final StyleSheet stylesheet) throws ValidationException {
        validate(stylesheet.getRules());
    }

    private void validate(final List<? extends NodeRule> rules) throws ValidationException {
        for (final NodeRule rule : rules) {
            if (validators.containsKey(rule.getClass())) {
                validators.get(rule.getClass()).validate(rule, limit);
            }
        }
    }
}
