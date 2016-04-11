/*
 * CSS Splitter Maven Plugin
 * http://www.gabrys.biz/projects/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *      https://raw.githubusercontent.com/gabrysbiz/css-splitter-maven-plugin/master/src/main/resources/license.txt
 */
package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.List;

final class TreeUtils {

    private TreeUtils() {
        // blocks the possibility of create a new instance
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void fillNeighbors(final NodeRule parent, final List<? extends Node<?, ?>> children) {
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
