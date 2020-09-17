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

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class AnyMessagePrinter implements MessagePrinter {

    private final List<MessagePrinter> printers;

    AnyMessagePrinter(final Log logger, final boolean strict) {
        printers = new ArrayList<MessagePrinter>();
        addPrinter(new UnknownRuleSizeMessagePrinter(logger), printers);
        addPrinter(new ComplexRuleInvalidContentMessagePrinter(logger, strict), printers);
    }

    // for tests
    AnyMessagePrinter(final List<MessagePrinter> printers) {
        this.printers = new ArrayList<MessagePrinter>(printers);
    }

    private static void addPrinter(final MessagePrinter printer, final List<MessagePrinter> printers) {
        if (printer.isEnabled()) {
            printers.add(printer);
        }
    }

    @Override
    public boolean isEnabled() {
        return !printers.isEmpty();
    }

    @Override
    public boolean isSupportedType(final NodeRule rule) {
        return true;
    }

    @Override
    public void print(final NodeRule rule) {
        for (final MessagePrinter printer : printers) {
            if (printer.isSupportedType(rule)) {
                printer.print(rule);
            }
        }
    }
}
