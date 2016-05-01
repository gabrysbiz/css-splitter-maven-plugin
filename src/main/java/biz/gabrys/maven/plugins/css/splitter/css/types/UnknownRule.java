/**
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

import java.util.Arrays;
import java.util.List;

public class UnknownRule extends AbstractTextRule {

    private final String code;

    public UnknownRule(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(code);
    }
}
