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

import java.util.List;
import java.util.regex.Pattern;

public class UnknownRule extends AbstractNodeRule {

    private static final Pattern NEW_LINE_REGEX = Pattern.compile("\\n");

    private final String code;

    public UnknownRule(final String code) {
        this.code = code;
    }

    @Override
    protected void fillLines(final List<String> lines) {
        for (final String line : NEW_LINE_REGEX.split(code, 0)) {
            lines.add(line);
        }
    }

    @Override
    protected int getSize2() {
        return new UnknownRuleCounter().count(this);
    }
}
