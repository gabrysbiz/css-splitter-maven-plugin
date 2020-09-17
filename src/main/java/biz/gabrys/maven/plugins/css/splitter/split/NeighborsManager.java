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
package biz.gabrys.maven.plugins.css.splitter.split;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class NeighborsManager {

    void fill(final NodeRule rule, final NodeRule first, final NodeRule second) {
        final NodeRule parent = rule.getParent();
        first.setParent(parent);
        second.setParent(parent);

        final NodeRule previous = rule.getPrevious();
        first.setPrevious(previous);
        if (previous != null) {
            previous.setNext(first);
        }

        first.setNext(second);
        second.setPrevious(first);

        final NodeRule next = rule.getNext();
        second.setNext(next);
        if (next != null) {
            next.setPrevious(second);
        }
    }
}
