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
package biz.gabrys.maven.plugins.css.splitter.message;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

class UnknownRuleSizeMessagePrinter extends AbstractMessagePrinter<UnknownRule> {

    private final Log logger;

    UnknownRuleSizeMessagePrinter(final Log logger) {
        super(UnknownRule.class);
        this.logger = logger;
    }

    public boolean isEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    protected void print2(final UnknownRule rule) {
        logger.debug("Found non-standard (unknown) rule:\n" + rule.getCode());
        final int size = rule.size();
        logger.debug(String.format("I guess it contains %d propert%s.", size, size != 1 ? "ies" : "y"));
    }
}
