/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.css.type;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

public class ComplexRule extends AbstractNodeRule {

    private final String type;
    private final List<String> selectors;
    private final List<NodeRule> rules;

    public ComplexRule(final String type, final List<String> selectors, final List<? extends NodeRule> rules) {
        TreeUtils.fillNeighbors(this, rules);
        this.type = type;
        this.selectors = new ArrayList<String>(selectors);
        this.rules = new ArrayList<NodeRule>(rules);
    }

    public String getType() {
        return type;
    }

    public List<String> getSelectors() {
        return new ArrayList<String>(selectors);
    }

    public List<NodeRule> getRules() {
        return new ArrayList<NodeRule>(rules);
    }

    @Override
    protected void fillLines(final List<String> lines) {
        lines.add(type + ' ' + StringUtils.join(selectors.iterator(), ", ") + " {");
        for (final NodeRule rule : rules) {
            final String[] childLines = rule.getLines();
            for (final String childLine : childLines) {
                lines.add(INDENTATION + childLine);
            }
        }
        lines.add("}");
    }

    @Override
    protected int getSize2() {
        int size = 0;
        for (final NodeRule rule : rules) {
            size += rule.getSize();
        }
        if (size == 0) {
            return 1;
        }
        return size;
    }
}
