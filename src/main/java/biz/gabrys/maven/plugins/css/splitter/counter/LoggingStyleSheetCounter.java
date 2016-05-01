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
package biz.gabrys.maven.plugins.css.splitter.counter;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public class LoggingStyleSheetCounter implements StyleSheetCounter {

    private final StyleSheetCounter counter;
    private final Log logger;

    public LoggingStyleSheetCounter(final StyleSheetCounter counter, final Log logger) {
        this.counter = counter;
        this.logger = logger;
    }

    public int count(final StyleSheet stylesheet) {
        final int value = counter.count(stylesheet);
        logger.info(String.format("Stylesheet contains %d rule%s.", value, value != 1 ? 's' : ""));
        return value;
    }
}
