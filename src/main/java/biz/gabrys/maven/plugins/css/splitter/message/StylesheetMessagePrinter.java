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
package biz.gabrys.maven.plugins.css.splitter.message;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public class StylesheetMessagePrinter {

    private final MessagePrinter printer;

    public StylesheetMessagePrinter(final Log logger, final boolean strict) {
        printer = new TreeMessagePrinter(new AnyMessagePrinter(logger, strict));
    }

    // for tests
    StylesheetMessagePrinter(final MessagePrinter printer) {
        this.printer = printer;
    }

    public void print(final StyleSheet stylesheet) {
        if (printer.isEnabled()) {
            for (final NodeRule rule : stylesheet.getRules()) {
                printer.print(rule);
            }
        }
    }
}
