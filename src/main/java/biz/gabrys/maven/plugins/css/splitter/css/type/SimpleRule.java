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

import java.util.List;

public class SimpleRule extends AbstractNodeRule {

    private final String code;

    public SimpleRule(final String code) {
        this.code = code;
    }

    @Override
    protected void fillLines(final List<String> lines) {
        lines.add(code);
    }

    @Override
    protected int getSize2() {
        return 1;
    }
}
