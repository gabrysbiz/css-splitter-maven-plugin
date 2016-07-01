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
package biz.gabrys.maven.plugins.css.splitter.css.type;

public class NodeImpl<P, S> implements Node<P, S> {

    private P parent;
    private S previous;
    private S next;

    public P getParent() {
        return parent;
    }

    public void setParent(final P parent) {
        this.parent = parent;
    }

    public S getPrevious() {
        return previous;
    }

    public void setPrevious(final S previous) {
        this.previous = previous;
    }

    public S getNext() {
        return next;
    }

    public void setNext(final S next) {
        this.next = next;
    }
}
