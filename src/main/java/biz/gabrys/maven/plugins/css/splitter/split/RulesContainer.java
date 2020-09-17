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
package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.LinkedList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

class RulesContainer {

    protected final List<NodeRule> before = new LinkedList<NodeRule>();
    protected final List<NodeRule> after = new LinkedList<NodeRule>();

    RulesContainer() {
        // do nothing
    }

    // for tests
    RulesContainer(final List<NodeRule> before, final List<NodeRule> after) {
        this.before.addAll(before);
        this.after.addAll(after);
    }
}
