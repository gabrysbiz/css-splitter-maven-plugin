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
package biz.gabrys.maven.plugins.css.splitter.tree;

import java.util.List;

public interface OrderedTreeNode<T> {

    List<OrderedTreeNode<T>> getChildren();

    boolean hasValue();

    T getValue();

    int getOrder();

    int size();

    int getDepth();
}
