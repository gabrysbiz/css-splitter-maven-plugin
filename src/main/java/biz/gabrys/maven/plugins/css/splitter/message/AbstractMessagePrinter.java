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

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

abstract class AbstractMessagePrinter<T extends NodeRule> implements MessagePrinter {

    private final Class<T> clazz;

    protected AbstractMessagePrinter(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public final boolean isSupportedType(final NodeRule rule) {
        return rule != null && rule.getClass() == clazz;
    }

    public final void print(final NodeRule rule) {
        if (isSupportedType(rule)) {
            print2(clazz.cast(rule));
        }
    }

    protected abstract void print2(T rule);
}
