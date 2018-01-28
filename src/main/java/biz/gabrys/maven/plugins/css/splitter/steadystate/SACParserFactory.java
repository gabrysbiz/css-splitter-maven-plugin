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

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import com.steadystate.css.parser.SACParser;
import com.steadystate.css.parser.SACParserCSS1;
import com.steadystate.css.parser.SACParserCSS2;
import com.steadystate.css.parser.SACParserCSS21;
import com.steadystate.css.parser.SACParserCSS3;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

class SACParserFactory {

    private static final Map<Standard, Class<? extends SACParser>> PARSERS_BY_CSS_STANDARD;

    static {
        PARSERS_BY_CSS_STANDARD = new EnumMap<Standard, Class<? extends SACParser>>(Standard.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_1_0, SACParserCSS1.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_2_0, SACParserCSS2.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_2_1, SACParserCSS21.class);
        PARSERS_BY_CSS_STANDARD.put(Standard.VERSION_3_0, SACParserCSS3.class);
    }

    SACParser create(final Standard standard) {
        try {
            return PARSERS_BY_CSS_STANDARD.get(standard).getDeclaredConstructor().newInstance();
        } catch (final InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException(e);
        } catch (final InvocationTargetException e) {
            throw new IllegalStateException(e);
        } catch (final NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } catch (final SecurityException e) {
            throw new IllegalStateException(e);
        }
    }
}
