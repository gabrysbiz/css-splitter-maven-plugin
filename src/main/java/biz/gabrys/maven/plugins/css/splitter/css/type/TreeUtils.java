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
package biz.gabrys.maven.plugins.css.splitter.css.type;

final class TreeUtils {

    private TreeUtils() {
        // blocks the possibility of create a new instance
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void fillNeighbors(final NodeRule parent, final Iterable<? extends Node<?, ?>> children) {
        Node previous = null;
        for (final Node child : children) {
            child.setPrevious(previous);
            child.setParent(parent);
            child.setNext(null);
            if (previous != null) {
                previous.setNext(child);
            }
            previous = child;
        }
    }
}
