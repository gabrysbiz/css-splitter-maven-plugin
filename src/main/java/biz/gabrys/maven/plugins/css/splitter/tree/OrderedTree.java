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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderedTree<T> implements OrderedTreeNode<T> {

    public static final int MIN_NUMBER_OF_CHILDREN = 2;

    private final int numberOfChildren;
    private final List<OrderedTree<T>> children;
    private T value;
    private int order;

    public OrderedTree(final List<T> objects, final int numberOfChildren) {
        this(numberOfChildren);

        if (objects.isEmpty()) {
            return;
        }

        // root node is a leaf, so we need to create size - 1 additional leaves
        createLeaves(objects.size() - 1);
        fillLeaves(objects);
        fillOrder(0);
    }

    private OrderedTree(final int numberOfChildren) {
        if (numberOfChildren < MIN_NUMBER_OF_CHILDREN) {
            throw new IllegalArgumentException("Number of children cannot be smaller than 2! Current value: " + numberOfChildren);
        }
        this.numberOfChildren = numberOfChildren;
        children = new ArrayList<OrderedTree<T>>(numberOfChildren);
    }

    @Override
    public List<OrderedTreeNode<T>> getChildren() {
        return new ArrayList<OrderedTreeNode<T>>(children);
    }

    @Override
    public boolean hasValue() {
        return value != null;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public int size() {
        int size = hasValue() ? 1 : 0;
        for (final OrderedTree<T> child : children) {
            if (!child.isLeaf()) {
                ++size;
            }
            size += child.size();
        }
        return size;
    }

    @Override
    public int getDepth() {
        if (isLeaf()) {
            return 0;
        }
        int max = 0;
        for (final OrderedTree<T> child : children) {
            final int tmp = child.getDepth();
            if (tmp > max) {
                max = tmp;
            }
        }
        return 1 + max;
    }

    private void createLeaves(final int count) {
        for (int i = 0; i < count; ++i) {
            createLeaf();
        }
    }

    private void createLeaf() {
        if (isLeaf()) {
            children.add(new OrderedTree<T>(numberOfChildren));
            children.add(new OrderedTree<T>(numberOfChildren));

        } else if (children.size() < numberOfChildren) {
            children.add(new OrderedTree<T>(numberOfChildren));

        } else {
            OrderedTree<T> node = getNotFullyFilledNode();
            if (node == null) {
                node = findNearestLeafNode();
            }
            node.createLeaf();
        }
    }

    private boolean isLeaf() {
        return children.isEmpty();
    }

    private OrderedTree<T> getNotFullyFilledNode() {
        if (isLeaf()) {
            return null;
        }

        if (children.size() < numberOfChildren) {
            return this;
        }

        for (final OrderedTree<T> child : children) {
            final OrderedTree<T> node = child.getNotFullyFilledNode();
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    private OrderedTree<T> findNearestLeafNode() {
        final List<LeafWithDistance<T>> leaves = getLeavesWithDistances();
        LeafWithDistance<T> leafWithDistance = leaves.get(0);
        OrderedTree<T> leaf = leafWithDistance.leaf;
        int distance = leafWithDistance.distance;
        for (int i = 1; i < leaves.size(); ++i) {
            leafWithDistance = leaves.get(i);
            if (leafWithDistance.distance < distance) {
                leaf = leafWithDistance.leaf;
                distance = leafWithDistance.distance;
            }
        }
        return leaf;
    }

    private List<LeafWithDistance<T>> getLeavesWithDistances() {
        if (isLeaf()) {
            final List<LeafWithDistance<T>> leaves = new ArrayList<LeafWithDistance<T>>();
            leaves.add(new LeafWithDistance<T>(this, 0));
            return leaves;
        }

        final List<LeafWithDistance<T>> leaves = new LinkedList<LeafWithDistance<T>>();
        for (final OrderedTree<T> child : children) {
            for (final LeafWithDistance<T> leafWithDistance : child.getLeavesWithDistances()) {
                leaves.add(new LeafWithDistance<T>(leafWithDistance.leaf, leafWithDistance.distance + 1));
            }
        }
        return leaves;
    }

    private void fillLeaves(final List<T> objects) {
        final List<OrderedTree<T>> leaves = getLeaves();
        if (leaves.size() != objects.size()) {
            throw new IllegalStateException(
                    String.format("Leaves quantity (%d) is other that objects quantity (%d)!", leaves.size(), objects.size()));
        }
        for (int i = 0; i < objects.size(); ++i) {
            leaves.get(i).value = objects.get(i);
        }
    }

    private List<OrderedTree<T>> getLeaves() {
        final List<LeafWithDistance<T>> leavesWithDistances = getLeavesWithDistances();
        final List<OrderedTree<T>> leaves = new ArrayList<OrderedTree<T>>(leavesWithDistances.size());
        for (final LeafWithDistance<T> leafWithDistance : leavesWithDistances) {
            leaves.add(leafWithDistance.leaf);
        }
        return leaves;
    }

    private int fillOrder(final int index) {
        order = index;
        int childOrder = index;
        for (final OrderedTree<T> child : children) {
            ++childOrder;
            childOrder = child.fillOrder(childOrder);
        }
        return childOrder;
    }

    private static final class LeafWithDistance<T> {

        private final OrderedTree<T> leaf;
        private final int distance;

        private LeafWithDistance(final OrderedTree<T> leaf, final int distance) {
            this.leaf = leaf;
            this.distance = distance;
        }
    }
}
