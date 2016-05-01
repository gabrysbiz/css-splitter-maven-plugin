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
package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.LinkedList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class RulesContainer<T extends NodeRule> {

    protected final List<T> before = new LinkedList<T>();
    protected final List<T> after = new LinkedList<T>();

    RulesContainer() {
        // do nothing
    }

    // for tests
    RulesContainer(final List<T> before, final List<T> after) {
        this.before.addAll(before);
        this.after.addAll(after);
    }
}
