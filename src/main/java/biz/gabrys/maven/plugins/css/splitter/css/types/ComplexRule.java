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
import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

public class ComplexRule extends AbstractTextRule {

    private final String type;
    private final List<String> selectors;
    private final List<StyleRule> rules;

    public ComplexRule(final String type, final List<String> selectors, final List<StyleRule> rules) {
        TreeUtils.fillNeighbors(this, rules);
        this.type = type;
        this.selectors = new ArrayList<String>(selectors);
        this.rules = new ArrayList<StyleRule>(rules);
    }

    public String getType() {
        return type;
    }

    public List<String> getSelectors() {
        return new ArrayList<String>(selectors);
    }

    public List<StyleRule> getRules() {
        return new ArrayList<StyleRule>(rules);
    }

    @Override
    public List<String> getLines() {
        final List<String> lines = new LinkedList<String>();
        lines.add(type + ' ' + StringUtils.join(selectors.iterator(), ", ") + " {");
        for (final StyleRule rule : rules) {
            for (final String line : rule.getLines()) {
                lines.add(INDENTATION + line);
            }
        }
        lines.add("}");
        return lines;
    }
}
