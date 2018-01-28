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
package biz.gabrys.maven.plugins.css.splitter.compressor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.yahoo.platform.yui.compressor.CssCompressor;

public final class CodeCompressor {

    private final int lineBreak;

    public CodeCompressor(final int lineBreak) {
        this.lineBreak = lineBreak;
    }

    public String compress(final String css) {
        CssCompressor compressor;
        try {
            compressor = new CssCompressor(new StringReader(css));
        } catch (final IOException e) {
            throw new InitializationException(e);
        }
        final StringWriter compressed = new StringWriter();
        try {
            compressor.compress(compressed, lineBreak);
        } catch (final IOException e) {
            throw new CompressionException(e);
        }
        return compressed.toString();
    }
}
