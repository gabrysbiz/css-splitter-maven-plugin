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
package biz.gabrys.maven.plugins.css.splitter.logger;

import org.apache.maven.plugin.logging.Log;

public class LoggerStorage {

    private final Log logger;
    private final boolean verbose;

    public LoggerStorage(final Log logger, final boolean verbose) {
        this.logger = logger;
        this.verbose = verbose;
    }

    public Log getLogger() {
        return logger;
    }

    public boolean isVerbose() {
        return verbose;
    }
}
