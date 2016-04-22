/*
 * CSS Splitter Maven Plugin
 * http://www.gabrys.biz/projects/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *      https://raw.githubusercontent.com/gabrysbiz/css-splitter-maven-plugin/master/src/main/resources/license.txt
 */

package biz.gabrys.maven.plugins.css.splitter.css.types;

public class Node<P, S> {

    private P parent;
    private S previous;
    private S next;

    protected Node() {
        // do nothing
    }

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
