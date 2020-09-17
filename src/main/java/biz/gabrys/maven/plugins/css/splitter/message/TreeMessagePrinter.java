/*
 * CSS Splitter Maven Plugin
 * https://gabrysbiz.github.io/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015-2020 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.message;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class TreeMessagePrinter implements MessagePrinter {

    private final MessagePrinter printer;

    public TreeMessagePrinter(final MessagePrinter printer) {
        this.printer = printer;
    }

    @Override
    public boolean isEnabled() {
        return printer.isEnabled();
    }

    @Override
    public boolean isSupportedType(final NodeRule rule) {
        return true;
    }

    @Override
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
