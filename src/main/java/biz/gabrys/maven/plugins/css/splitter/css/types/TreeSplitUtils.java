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

public final class TreeSplitUtils {

    private TreeSplitUtils() {
        // blocks the possibility of create a new instance
    }

    // TODO add tests
    public static void fillNeighbors(final NodeRule rule, final NodeRule first, final NodeRule second) {
        first.setParent(rule.getParent());
        second.setParent(rule.getParent());

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
