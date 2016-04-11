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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.css.sac.Parser;

import com.steadystate.css.parser.SACParserCSS1;
import com.steadystate.css.parser.SACParserCSS2;
import com.steadystate.css.parser.SACParserCSS21;
import com.steadystate.css.parser.SACParserCSS3;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

class NativeParserFactory {

    private static final Map<Standard, Class<? extends Parser>> PARSERS_BY_CSS_STANDARD;

    static {
        PARSERS_BY_CSS_STANDARD = new ConcurrentHashMap<Standard, Class<? extends Parser>>();
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_1_0, SACParserCSS1.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_2_0, SACParserCSS2.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_2_1, SACParserCSS21.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_3_0, SACParserCSS3.class);
    }

    Parser create(final Standard standard) {
        try {
            return PARSERS_BY_CSS_STANDARD.get(standard).newInstance();
        } catch (final InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
