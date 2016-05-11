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

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractNodeRule extends NodeImpl<NodeRule, NodeRule> implements NodeRule {

    protected static final String INDENTATION = "  ";

    private Integer size;

    public String getCode() {
        final String[] lines = getLines();
        final StringBuilder code = new StringBuilder();
        code.append(lines[0]);
        for (int i = 1; i < lines.length; ++i) {
            code.append('\n');
            code.append(lines[i]);
        }
        return code.toString();
    }

    public String[] getLines() {
        final List<String> lines = new LinkedList<String>();
        fillLines(lines);
        return lines.toArray(new String[lines.size()]);
    }

    protected abstract void fillLines(List<String> lines);

    public int getSize() {
        if (size == null) {
            size = getSize2();
        }
        return size;
    }

    protected abstract int getSize2();

    @Override
    public String toString() {
        return getCode() + '\n';
    }
}
