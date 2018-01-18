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

import java.io.IOException;
import java.io.StringReader;

import org.apache.maven.plugin.logging.Log;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParser;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.steadystate.converter.StyleSheetConverter;

public class SteadyStateParser {

    private final Log logger;

    public SteadyStateParser(final Log logger) {
        this.logger = logger;
    }

    public StyleSheet parse(final String css, final ParserOptions options) {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser sacParser = factory.create(options.getStandard());
        sacParser.setIeStarHackAccepted(options.isStarHackAllowed());

        final CSSOMParser parser = new CSSOMParser(sacParser);

        final SteadyErrorHandler errorHandler = new SteadyErrorHandler(logger);
        parser.setErrorHandler(errorHandler);

        final InputSource source = new InputSource(new StringReader(css));
        CSSStyleSheet stylesheet;
        try {
            stylesheet = parser.parseStyleSheet(source, null, null);
        } catch (final IOException e) {
            throw new ParserException(e);
        }
        errorHandler.validate();

        final StyleSheetConverter converter = new StyleSheetConverter(options.getStandard(), options.isStrict());
        return converter.convert(stylesheet);
    }
}
