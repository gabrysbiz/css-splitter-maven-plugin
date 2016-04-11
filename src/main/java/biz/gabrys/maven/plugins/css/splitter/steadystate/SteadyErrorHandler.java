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

    void validate() throws ParserException {
        if (firstError != null) {
            throw new ParserException(firstError);
        }
    }

    public void warning(final CSSParseException exception) {
        logger.warn(convertExceptionToString(exception));
    }

    public void error(final CSSParseException exception) {
        logger.error(convertExceptionToString(exception));
        if (firstError == null) {
            firstError = exception;
        }
    }

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
