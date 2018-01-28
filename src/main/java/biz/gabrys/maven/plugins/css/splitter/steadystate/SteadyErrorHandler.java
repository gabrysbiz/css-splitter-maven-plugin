/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.apache.maven.plugin.logging.Log;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

class SteadyErrorHandler implements ErrorHandler {

    private final Log logger;
    private CSSParseException firstError;

    SteadyErrorHandler(final Log logger) {
        this.logger = logger;
    }

    void validate() {
        if (firstError != null) {
            throw new ParserException(firstError);
        }
    }

    @Override
    public void warning(final CSSParseException exception) {
        logger.warn(convertExceptionToString(exception));
    }

    @Override
    public void error(final CSSParseException exception) {
        fatalError(exception);
    }

    @Override
    public void fatalError(final CSSParseException exception) {
        logger.error(convertExceptionToString(exception));
        if (firstError == null) {
            firstError = exception;
        }
    }

    private static String convertExceptionToString(final CSSParseException exception) {
        final StringBuilder text = new StringBuilder();
        text.append('[');
        text.append(exception.getLineNumber());
        text.append(':');
        text.append(exception.getColumnNumber());
        text.append("] ");
        text.append(exception.getMessage());
        return text.toString();
    }
}
