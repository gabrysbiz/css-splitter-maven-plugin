package biz.gabrys.maven.plugins.css.splitter.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public final class OrderedTreeTest {

    @Test
    public void create_zeroObjects() {
        final OrderedTree<Object> tree = new OrderedTree<Object>(Collections.emptyList(), 2);

        assertThat(tree).isNotNull();
        assertThat(tree.hasValue()).isFalse();
        assertThat(tree.getValue()).isNull();
        assertThat(tree.getChildren()).isEmpty();
        assertThat(tree.size()).isZero();
        assertThat(tree.getOrder()).isZero();
        assertThat(tree.getDepth()).isZero();
    }

    @Test
    public void create_threeObjects_numberOfChildrenEqualToFive() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3), 5);

        assertThat(tree).isNotNull();
        assertThat(tree.hasValue()).isFalse();
        assertThat(tree.getValue()).isNull();
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertThat(children).hasSize(3);
        assertThat(tree.size()).isEqualTo(3);
        assertThat(tree.getOrder()).isZero();
        assertThat(tree.getDepth()).isEqualTo(1);

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertThat(child1).isNotNull();
        assertThat(child1.hasValue()).isTrue();
        assertThat(child1.getValue()).isEqualTo(obj1);
        assertThat(child1.getChildren()).isEmpty();
        assertThat(child1.size()).isEqualTo(1);
        assertThat(child1.getOrder()).isEqualTo(1);
        assertThat(child1.getDepth()).isZero();

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertThat(child2).isNotNull();
        assertThat(child2.hasValue()).isTrue();
        assertThat(child2.getValue()).isEqualTo(obj2);
        assertThat(child2.getChildren()).isEmpty();
        assertThat(child2.size()).isEqualTo(1);
        assertThat(child2.getOrder()).isEqualTo(2);
        assertThat(child2.getDepth()).isZero();

        final OrderedTreeNode<Object> child3 = children.get(2);
        assertThat(child3).isNotNull();
        assertThat(child3.hasValue()).isTrue();
        assertThat(child3.getValue()).isEqualTo(obj3);
        assertThat(child3.getChildren()).isEmpty();
        assertThat(child3.size()).isEqualTo(1);
        assertThat(child3.getOrder()).isEqualTo(3);
        assertThat(child3.getDepth()).isZero();
    }

    @Test
    public void create_fiveObjects_numberOfChildrenEqualToTwo() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);
        final Object obj4 = mock(Object.class);
        final Object obj5 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3, obj4, obj5), 2);

        assertThat(tree).isNotNull();
        assertThat(tree.hasValue()).isFalse();
        assertThat(tree.getValue()).isNull();
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertThat(children).hasSize(2);
        assertThat(tree.size()).isEqualTo(8);
        assertThat(tree.getOrder()).isZero();
        assertThat(tree.getDepth()).isEqualTo(3);

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertThat(child1).isNotNull();
        assertThat(child1.hasValue()).isFalse();
        assertThat(child1.getValue()).isNull();
        final List<OrderedTreeNode<Object>> child1Children = child1.getChildren();
        assertThat(child1Children).hasSize(2);
        assertThat(child1.size()).isEqualTo(4);
        assertThat(child1.getOrder()).isEqualTo(1);
        assertThat(child1.getDepth()).isEqualTo(2);

        final OrderedTreeNode<Object> child1Child1 = child1Children.get(0);
        assertThat(child1Child1).isNotNull();
        assertThat(child1Child1.hasValue()).isFalse();
        assertThat(child1Child1.getValue()).isNull();
        final List<OrderedTreeNode<Object>> child1Child1Children = child1Child1.getChildren();
        assertThat(child1Child1Children).hasSize(2);
        assertThat(child1Child1.size()).isEqualTo(2);
        assertThat(child1Child1.getOrder()).isEqualTo(2);
        assertThat(child1Child1.getDepth()).isEqualTo(1);

        final OrderedTreeNode<Object> child1Child1Child1 = child1Child1Children.get(0);
        assertThat(child1Child1Child1).isNotNull();
        assertThat(child1Child1Child1.hasValue()).isTrue();
        assertThat(child1Child1Child1.getValue()).isEqualTo(obj1);
        assertThat(child1Child1Child1.getChildren()).isEmpty();
        assertThat(child1Child1Child1.size()).isEqualTo(1);
        assertThat(child1Child1Child1.getOrder()).isEqualTo(3);
        assertThat(child1Child1Child1.getDepth()).isZero();

        final OrderedTreeNode<Object> child1Child1Child2 = child1Child1Children.get(1);
        assertThat(child1Child1Child2).isNotNull();
        assertThat(child1Child1Child2.hasValue()).isTrue();
        assertThat(child1Child1Child2.getValue()).isEqualTo(obj2);
        assertThat(child1Child1Child2.getChildren()).isEmpty();
        assertThat(child1Child1Child2.size()).isEqualTo(1);
        assertThat(child1Child1Child2.getOrder()).isEqualTo(4);
        assertThat(child1Child1Child2.getDepth()).isZero();

        final OrderedTreeNode<Object> child1Child2 = child1Children.get(1);
        assertThat(child1Child2).isNotNull();
        assertThat(child1Child2.hasValue()).isTrue();
        assertThat(child1Child2.getValue()).isEqualTo(obj3);
        assertThat(child1Child2.getChildren()).isEmpty();
        assertThat(child1Child2.size()).isEqualTo(1);
        assertThat(child1Child2.getOrder()).isEqualTo(5);
        assertThat(child1Child2.getDepth()).isZero();

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertThat(child2).isNotNull();
        assertThat(child2.hasValue()).isFalse();
        assertThat(child2.getValue()).isNull();
        final List<OrderedTreeNode<Object>> child2Children = child2.getChildren();
        assertThat(child2Children).hasSize(2);
        assertThat(child2.size()).isEqualTo(2);
        assertThat(child2.getOrder()).isEqualTo(6);
        assertThat(child2.getDepth()).isEqualTo(1);

        final OrderedTreeNode<Object> child2Child1 = child2Children.get(0);
        assertThat(child2Child1).isNotNull();
        assertThat(child2Child1.hasValue()).isTrue();
        assertThat(child2Child1.getValue()).isEqualTo(obj4);
        assertThat(child2Child1.getChildren()).isEmpty();
        assertThat(child2Child1.size()).isEqualTo(1);
        assertThat(child2Child1.getOrder()).isEqualTo(7);
        assertThat(child2Child1.getDepth()).isZero();

        final OrderedTreeNode<Object> child2Child2 = child2Children.get(1);
        assertThat(child2Child2).isNotNull();
        assertThat(child2Child2.hasValue()).isTrue();
        assertThat(child2Child2.getValue()).isEqualTo(obj5);
        assertThat(child2Child2.getChildren()).isEmpty();
        assertThat(child2Child2.size()).isEqualTo(1);
        assertThat(child2Child2.getOrder()).isEqualTo(8);
        assertThat(child2Child2.getDepth()).isZero();
    }

    @Test
    public void create_fiveObjects_numberOfChildrenEqualToThree() {
        final Object obj1 = mock(Object.class);
        final Object obj2 = mock(Object.class);
        final Object obj3 = mock(Object.class);
        final Object obj4 = mock(Object.class);
        final Object obj5 = mock(Object.class);

        final OrderedTree<Object> tree = new OrderedTree<Object>(Arrays.asList(obj1, obj2, obj3, obj4, obj5), 3);

        assertThat(tree).isNotNull();
        assertThat(tree.hasValue()).isFalse();
        assertThat(tree.getValue()).isNull();
        final List<OrderedTreeNode<Object>> children = tree.getChildren();
        assertThat(children).hasSize(3);
        assertThat(tree.size()).isEqualTo(6);
        assertThat(tree.getOrder()).isZero();
        assertThat(tree.getDepth()).isEqualTo(2);

        final OrderedTreeNode<Object> child1 = children.get(0);
        assertThat(child1).isNotNull();
        assertThat(child1.hasValue()).isFalse();
        assertThat(child1.getValue()).isNull();
        final List<OrderedTreeNode<Object>> child1Children = child1.getChildren();
        assertThat(child1Children).hasSize(3);
        assertThat(child1.size()).isEqualTo(3);
        assertThat(child1.getOrder()).isEqualTo(1);
        assertThat(child1.getDepth()).isEqualTo(1);

        final OrderedTreeNode<Object> child1Child1 = child1Children.get(0);
        assertThat(child1Child1).isNotNull();
        assertThat(child1Child1.hasValue()).isTrue();
        assertThat(child1Child1.getValue()).isEqualTo(obj1);
        assertThat(child1Child1.getChildren()).isEmpty();
        assertThat(child1Child1.size()).isEqualTo(1);
        assertThat(child1Child1.getOrder()).isEqualTo(2);
        assertThat(child1Child1.getDepth()).isZero();

        final OrderedTreeNode<Object> child1Child2 = child1Children.get(1);
        assertThat(child1Child2).isNotNull();
        assertThat(child1Child2.hasValue()).isTrue();
        assertThat(child1Child2.getValue()).isEqualTo(obj2);
        assertThat(child1Child2.getChildren()).isEmpty();
        assertThat(child1Child2.size()).isEqualTo(1);
        assertThat(child1Child2.getOrder()).isEqualTo(3);
        assertThat(child1Child2.getDepth()).isZero();

        final OrderedTreeNode<Object> child1Child3 = child1Children.get(2);
        assertThat(child1Child3).isNotNull();
        assertThat(child1Child3.hasValue()).isTrue();
        assertThat(child1Child3.getValue()).isEqualTo(obj3);
        assertThat(child1Child3.getChildren()).isEmpty();
        assertThat(child1Child3.size()).isEqualTo(1);
        assertThat(child1Child3.getOrder()).isEqualTo(4);
        assertThat(child1Child3.getDepth()).isZero();

        final OrderedTreeNode<Object> child2 = children.get(1);
        assertThat(child2).isNotNull();
        assertThat(child2.hasValue()).isTrue();
        assertThat(child2.getValue()).isEqualTo(obj4);
        assertThat(child2.getChildren()).isEmpty();
        assertThat(child2.size()).isEqualTo(1);
        assertThat(child2.getOrder()).isEqualTo(5);
        assertThat(child2.getDepth()).isZero();

        final OrderedTreeNode<Object> child3 = children.get(2);
        assertThat(child3).isNotNull();
        assertThat(child3.hasValue()).isTrue();
        assertThat(child3.getValue()).isEqualTo(obj5);
        assertThat(child3.getChildren()).isEmpty();
        assertThat(child3.size()).isEqualTo(1);
        assertThat(child3.getOrder()).isEqualTo(6);
        assertThat(child3.getDepth()).isZero();
    }
}
