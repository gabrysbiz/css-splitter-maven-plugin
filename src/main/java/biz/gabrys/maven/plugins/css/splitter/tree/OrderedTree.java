package biz.gabrys.maven.plugins.css.splitter.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderedTree<T> implements TreeNode<T> {

    private final int numberOfChildren;
    private final List<OrderedTree<T>> children;
    private T value;

    public OrderedTree(final List<T> objects, final int numberOfChildren) {
        this(numberOfChildren);

        if (objects.isEmpty()) {
            return;
        }

        // root node is a leaf, so we need to create size - 1 additional leaves
        for (int i = 0; i < objects.size() - 1; ++i) {
            createLeaf();
        }
        final List<OrderedTree<T>> leaves = getLeaves();
        if (leaves.size() != objects.size()) {
            throw new IllegalStateException(
                    String.format("Leaves quantity (%d) is other that objects quantity (%d)!", leaves.size(), objects.size()));
        }
        for (int i = 0; i < objects.size(); ++i) {
            leaves.get(i).value = objects.get(i);
        }
    }

    private OrderedTree(final int numberOfChildren) {
        if (numberOfChildren < 2) {
            throw new IllegalArgumentException("Number of children cannot be smaller than 2! Current value: " + numberOfChildren);
        }
        this.numberOfChildren = numberOfChildren;
        children = new ArrayList<OrderedTree<T>>(numberOfChildren);
    }

    public List<TreeNode<T>> getChildren() {
        final List<TreeNode<T>> nodes = new ArrayList<TreeNode<T>>(children.size());
        for (final TreeNode<T> child : children) {
            nodes.add(child);
        }
        return nodes;
    }

    public boolean hasValue() {
        return value != null;
    }

    public T getValue() {
        return value;
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

    private List<OrderedTree<T>> getLeaves() {
        final List<LeafWithDistance<T>> leavesWithDistances = getLeavesWithDistances();
        final List<OrderedTree<T>> leaves = new ArrayList<OrderedTree<T>>(leavesWithDistances.size());
        for (final LeafWithDistance<T> leafWithDistance : leavesWithDistances) {
            leaves.add(leafWithDistance.leaf);
        }
        return leaves;
    }

    private static class LeafWithDistance<T> {

        private final OrderedTree<T> leaf;
        private final int distance;

        private LeafWithDistance(final OrderedTree<T> leaf, final int distance) {
            this.leaf = leaf;
            this.distance = distance;
        }
    }
}
