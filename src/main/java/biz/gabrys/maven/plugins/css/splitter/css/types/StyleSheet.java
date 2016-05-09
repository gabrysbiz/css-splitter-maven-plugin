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
package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.ArrayList;
import java.util.List;

public class StyleSheet {

    private final List<NodeRule> rules;
    private Integer size;

    public StyleSheet(final List<NodeRule> rules) {
        TreeUtils.fillNeighbors(null, rules);
        this.rules = new ArrayList<NodeRule>(rules);
    }

    public List<NodeRule> getRules() {
        return new ArrayList<NodeRule>(rules);
    }

    @Override
    public String toString() {
        final StringBuilder css = new StringBuilder();
        for (final NodeRule rule : rules) {
            css.append(rule);
        }
        return css.toString();
    }

    public int size() {
        if (size == null) {
            size = 0;
            for (final NodeRule child : rules) {
                size += child.size();
            }
        }
        return size;
    }
}
