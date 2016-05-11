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

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class TreeMessagePrinter implements MessagePrinter {

    private final MessagePrinter printer;

    public TreeMessagePrinter(final MessagePrinter printer) {
        this.printer = printer;
    }

    public boolean isEnabled() {
        return printer.isEnabled();
    }

    public boolean isSupportedType(final NodeRule rule) {
        return true;
    }

    public void print(final NodeRule rule) {
        if (printer.isSupportedType(rule)) {
            printer.print(rule);
        }
        if (rule instanceof ComplexRule) {
            for (final NodeRule child : ((ComplexRule) rule).getRules()) {
                print(child);
            }
        }
    }
}
