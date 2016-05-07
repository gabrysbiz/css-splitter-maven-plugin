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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class AnyMessagePrinter implements MessagePrinter {

    private final List<MessagePrinter> printers;

    AnyMessagePrinter(final Log logger) {
        final MessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);
        if (printer.isEnabled()) {
            printers = Arrays.<MessagePrinter>asList(printer);
        } else {
            printers = Collections.emptyList();
        }
    }

    // for tests
    AnyMessagePrinter(final List<MessagePrinter> printers) {
        this.printers = printers;
    }

    public boolean isEnabled() {
        return !printers.isEmpty();
    }

    public boolean isSupportedType(final NodeRule rule) {
        return true;
    }

    public void print(final NodeRule rule) {
        for (final MessagePrinter printer : printers) {
            if (printer.isSupportedType(rule)) {
                printer.print(rule);
            }
        }
    }
}
