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
package biz.gabrys.maven.plugins.css.splitter.split;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class SplitResult {

    private final NodeRule before;
    private final NodeRule after;

    SplitResult(final NodeRule before, final NodeRule after) {
        this.before = before;
        this.after = after;
    }

    NodeRule getBefore() {
        return before;
    }

    NodeRule getAfter() {
        return after;
    }
}
