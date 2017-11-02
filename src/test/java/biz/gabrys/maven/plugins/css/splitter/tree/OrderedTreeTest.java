package biz.gabrys.maven.plugins.css.splitter.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public final class OrderedTreeTest {

    @Test
    public void create_zeroObjects() {
        final OrderedTree<Object> tree = new OrderedTree<Object>(Collections.emptyList(), 2);

        assertFalse("Root should not contain value", tree.hasValue());
        assertNull("Root value should be equal to null", tree.getValue());
        assertEquals("Root children size", 0, tree.getChildren().size());
        assertEquals("Root tree size", 0, tree.size());
        assertEquals("Root order", 0, tree.getOrder());
        assertEquals("Root depth", 0, tree.getDepth());
    }

    @Test
    public void create_threeObjects_numberOfChildrenEqualToFive() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3), 5);

        assertFalse("Root should not contain value", tree.hasValue());
        assertNull("Root value should be equal to null", tree.getValue());
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertEquals("Root children size", 3, children.size());
        assertEquals("Root tree size", 3, tree.size());
        assertEquals("Root order", 0, tree.getOrder());
        assertEquals("Root depth", 1, tree.getDepth());

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertTrue("First child should contain value", child1.hasValue());
        assertEquals("First child value should be equal to obj1", obj1, child1.getValue());
        assertEquals("First child children size", 0, child1.getChildren().size());
        assertEquals("First child tree size", 1, child1.size());
        assertEquals("First child order", 1, child1.getOrder());
        assertEquals("First child depth", 0, child1.getDepth());

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertTrue("Second child should contain value", child2.hasValue());
        assertEquals("Second child value should be equal to obj2", obj2, child2.getValue());
        assertEquals("Second child children size", 0, child2.getChildren().size());
        assertEquals("Second child tree size", 1, child2.size());
        assertEquals("Second child order", 2, child2.getOrder());
        assertEquals("Second child depth", 0, child2.getDepth());

        final OrderedTreeNode<Object> child3 = children.get(2);
        assertTrue("Third child should contain value", child3.hasValue());
        assertEquals("Third child value should be equal to obj3", obj3, child3.getValue());
        assertEquals("Third child children size", 0, child3.getChildren().size());
        assertEquals("Third child tree size", 1, child3.size());
        assertEquals("Third child order", 3, child3.getOrder());
        assertEquals("Third child depth", 0, child3.getDepth());
    }

    @Test
    public void create_fiveObjects_numberOfChildrenEqualToTwo() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);
        final Object obj4 = mock(Object.class);
        final Object obj5 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3, obj4, obj5), 2);

        assertFalse("Root should not contain value", tree.hasValue());
        assertNull("Root value should be equal to null", tree.getValue());
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertEquals("Root children size", 2, children.size());
        assertEquals("Root tree size", 8, tree.size());
        assertEquals("Root order", 0, tree.getOrder());
        assertEquals("Root depth", 3, tree.getDepth());

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertFalse("Root first child should not contain value", child1.hasValue());
        assertNull("Root first child value should be equal to null", child1.getValue());
        final List<OrderedTreeNode<Object>> child1Children = child1.getChildren();
        assertEquals("Root first child children size", 2, child1Children.size());
        assertEquals("Root first child tree size", 4, child1.size());
        assertEquals("Root first child order", 1, child1.getOrder());
        assertEquals("Root first child depth", 2, child1.getDepth());

        final OrderedTreeNode<Object> child1Child1 = child1Children.get(0);
        assertFalse("Root first child first child should not contain value", child1Child1.hasValue());
        assertNull("Root first child first child value should be equal to null", child1Child1.getValue());
        final List<OrderedTreeNode<Object>> child1Child1Children = child1Child1.getChildren();
        assertEquals("Root first child first child children size", 2, child1Child1Children.size());
        assertEquals("Root first child first child tree size", 2, child1Child1.size());
        assertEquals("Root first child first child order", 2, child1Child1.getOrder());
        assertEquals("Root first child first child depth", 1, child1Child1.getDepth());
        final OrderedTreeNode<Object> child1Child1Child1 = child1Child1Children.get(0);
        assertTrue("Root first child first child first child should not contain value", child1Child1Child1.hasValue());
        assertEquals("Root first child first child first child value should be equal to obj1", obj1, child1Child1Child1.getValue());
        assertEquals("Root first child first child first child children size", 0, child1Child1Child1.getChildren().size());
        assertEquals("Root first child first child first child tree size", 1, child1Child1Child1.size());
        assertEquals("Root first child first child first child order", 3, child1Child1Child1.getOrder());
        assertEquals("Root first child first child first child depth", 0, child1Child1Child1.getDepth());
        final OrderedTreeNode<Object> child1Child1Child2 = child1Child1Children.get(1);
        assertTrue("Root first child first child second child should not contain value", child1Child1Child2.hasValue());
        assertEquals("Root first child first child second child value should be equal to obj2", obj2, child1Child1Child2.getValue());
        assertEquals("Root first child first child second child children size", 0, child1Child1Child2.getChildren().size());
        assertEquals("Root first child first child second child tree size", 1, child1Child1Child2.size());
        assertEquals("Root first child first child second child order", 4, child1Child1Child2.getOrder());
        assertEquals("Root first child first child second child depth", 0, child1Child1Child2.getDepth());

        final OrderedTreeNode<Object> child1Child2 = child1Children.get(1);
        assertTrue("Root first child second child should contain value", child1Child2.hasValue());
        assertEquals("Root first child second child value should be equal to obj3", obj3, child1Child2.getValue());
        assertEquals("Root first child second child children size", 0, child1Child2.getChildren().size());
        assertEquals("Root first child second child tree size", 1, child1Child2.size());
        assertEquals("Root first child second child order", 5, child1Child2.getOrder());
        assertEquals("Root first child second child depth", 0, child1Child2.getDepth());

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertFalse("Root second child should not contain value", child2.hasValue());
        assertNull("Root second child value should be equal to null", child2.getValue());
        final List<OrderedTreeNode<Object>> child2Children = child2.getChildren();
        assertEquals("Root second child children size", 2, child2Children.size());
        assertEquals("Root second child tree size", 2, child2.size());
        assertEquals("Root second child order", 6, child2.getOrder());
        assertEquals("Root second child depth", 1, child2.getDepth());
        final OrderedTreeNode<Object> child2Child1 = child2Children.get(0);
        assertTrue("Root second child first child should contain value", child2Child1.hasValue());
        assertEquals("Root second child first child value should be equal to obj4", obj4, child2Child1.getValue());
        assertEquals("Root second child first child children size", 0, child2Child1.getChildren().size());
        assertEquals("Root second child first child tree size", 1, child2Child1.size());
        assertEquals("Root second child first child order", 7, child2Child1.getOrder());
        assertEquals("Root second child first child depth", 0, child2Child1.getDepth());
        final OrderedTreeNode<Object> child2Child2 = child2Children.get(1);
        assertTrue("Root second child second child should contain value", child2Child2.hasValue());
        assertEquals("Root second child second child value should be equal to obj5", obj5, child2Child2.getValue());
        assertEquals("Root second child second child children size", 0, child2Child2.getChildren().size());
        assertEquals("Root second child second child tree size", 1, child2Child2.size());
        assertEquals("Root second child second child order", 8, child2Child2.getOrder());
        assertEquals("Root second child second child depth", 0, child2Child2.getDepth());
    }

    @Test
    public void create_fiveObjects_numberOfChildrenEqualToThree() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);
        final Object obj4 = mock(Object.class);
        final Object obj5 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3, obj4, obj5), 3);

        assertFalse("Root should not contain value", tree.hasValue());
        assertNull("Root value should be equal to null", tree.getValue());
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertEquals("Root children size", 3, children.size());
        assertEquals("Root tree size", 6, tree.size());
        assertEquals("Root order", 0, tree.getOrder());
        assertEquals("Root depth", 2, tree.getDepth());

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertFalse("Root first child should not contain value", child1.hasValue());
        assertNull("Root first child value should be equal to null", child1.getValue());
        final List<OrderedTreeNode<Object>> child1Children = child1.getChildren();
        assertEquals("Root first child children size", 3, child1Children.size());
        assertEquals("Root first child tree size", 3, child1.size());
        assertEquals("Root first child order", 1, child1.getOrder());
        assertEquals("Root first child depth", 1, child1.getDepth());

        final OrderedTreeNode<Object> child1Child1 = child1Children.get(0);
        assertTrue("Root first child first child should contain value", child1Child1.hasValue());
        assertEquals("Root first child first child value should be equal to obj1", obj1, child1Child1.getValue());
        assertEquals("Root first child first child children size", 0, child1Child1.getChildren().size());
        assertEquals("Root first child first child tree size", 1, child1Child1.size());
        assertEquals("Root first child first child order", 2, child1Child1.getOrder());
        assertEquals("Root first child first child depth", 0, child1Child1.getDepth());
        final OrderedTreeNode<Object> child1Child2 = child1Children.get(1);
        assertTrue("Root first child second child should contain value", child1Child2.hasValue());
        assertEquals("Root first child second child value should be equal to obj2", obj2, child1Child2.getValue());
        assertEquals("Root first child second child children size", 0, child1Child2.getChildren().size());
        assertEquals("Root first child second child tree size", 1, child1Child2.size());
        assertEquals("Root first child second child order", 3, child1Child2.getOrder());
        assertEquals("Root first child second child depth", 0, child1Child2.getDepth());
        final OrderedTreeNode<Object> child1Child3 = child1Children.get(2);
        assertTrue("Root first child second child should contain value", child1Child3.hasValue());
        assertEquals("Root first child second child value should be equal to obj3", obj3, child1Child3.getValue());
        assertEquals("Root first child second child children size", 0, child1Child3.getChildren().size());
        assertEquals("Root first child second child tree size", 1, child1Child3.size());
        assertEquals("Root first child second child order", 4, child1Child3.getOrder());
        assertEquals("Root first child second child depth", 0, child1Child3.getDepth());

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertTrue("Root second child should contain value", child2.hasValue());
        assertEquals("Root second child value should be equal to obj4", obj4, child2.getValue());
        assertEquals("Root second child children size", 0, child2.getChildren().size());
        assertEquals("Root second child tree size", 1, child2.size());
        assertEquals("Root second child order", 5, child2.getOrder());
        assertEquals("Root second child depth", 0, child2.getDepth());

        final OrderedTreeNode<Object> child3 = children.get(2);
        assertTrue("Root third child should contain value", child3.hasValue());
        assertEquals("Root third child value should be equal to obj5", obj5, child3.getValue());
        assertEquals("Root third child children size", 0, child3.getChildren().size());
        assertEquals("Root third child tree size", 1, child3.size());
        assertEquals("Root third child order", 6, child3.getOrder());
        assertEquals("Root third child depth", 0, child3.getDepth());
    }
}
