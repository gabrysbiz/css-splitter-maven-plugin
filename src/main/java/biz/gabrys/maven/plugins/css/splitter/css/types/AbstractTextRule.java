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

import java.util.List;

public abstract class AbstractTextRule extends NodeRuleImpl {

    protected static final String INDENTATION = "  ";

    protected abstract List<String> getLines();

    @Override
    public final String toString() {
        final StringBuilder css = new StringBuilder();
        for (final String line : getLines()) {
            css.append(line);
            css.append('\n');
        }
        return css.toString();
    }
}
