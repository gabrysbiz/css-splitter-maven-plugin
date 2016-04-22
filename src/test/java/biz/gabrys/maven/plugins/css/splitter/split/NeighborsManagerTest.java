package biz.gabrys.maven.plugins.css.splitter.split;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class NeighborsManagerTest {

    @Test
    public void fill_fullNeighbors() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final NodeRule parent = Mockito.mock(NodeRule.class);
        final NodeRule previous = Mockito.mock(NodeRule.class);
        final NodeRule next = Mockito.mock(NodeRule.class);
        Mockito.when(rule.getParent()).thenReturn(parent);
        Mockito.when(rule.getPrevious()).thenReturn(previous);
        Mockito.when(rule.getNext()).thenReturn(next);

        final NodeRule first = Mockito.mock(NodeRule.class);
        final NodeRule second = Mockito.mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        Mockito.verify(first).setParent(parent);
        Mockito.verify(second).setParent(parent);
        Mockito.verify(first).setPrevious(previous);
        Mockito.verify(previous).setNext(first);
        Mockito.verify(first).setNext(second);
        Mockito.verify(second).setPrevious(first);
        Mockito.verify(second).setNext(next);
        Mockito.verify(next).setPrevious(second);
        Mockito.verifyNoMoreInteractions(previous, first, second, next);
    }

    @Test
    public void fill_parentIsNull() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final NodeRule parent = null;
        final NodeRule previous = Mockito.mock(NodeRule.class);
        final NodeRule next = Mockito.mock(NodeRule.class);
        Mockito.when(rule.getParent()).thenReturn(parent);
        Mockito.when(rule.getPrevious()).thenReturn(previous);
        Mockito.when(rule.getNext()).thenReturn(next);

        final NodeRule first = Mockito.mock(NodeRule.class);
        final NodeRule second = Mockito.mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        Mockito.verify(first).setParent(parent);
        Mockito.verify(second).setParent(parent);
        Mockito.verify(first).setPrevious(previous);
        Mockito.verify(previous).setNext(first);
        Mockito.verify(first).setNext(second);
        Mockito.verify(second).setPrevious(first);
        Mockito.verify(second).setNext(next);
        Mockito.verify(next).setPrevious(second);
        Mockito.verifyNoMoreInteractions(previous, first, second, next);
    }

    @Test
    public void fill_previousIsNull() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final NodeRule parent = Mockito.mock(NodeRule.class);
        final NodeRule previous = null;
        final NodeRule next = Mockito.mock(NodeRule.class);
        Mockito.when(rule.getParent()).thenReturn(parent);
        Mockito.when(rule.getPrevious()).thenReturn(previous);
        Mockito.when(rule.getNext()).thenReturn(next);

        final NodeRule first = Mockito.mock(NodeRule.class);
        final NodeRule second = Mockito.mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        Mockito.verify(first).setParent(parent);
        Mockito.verify(second).setParent(parent);
        Mockito.verify(first).setPrevious(previous);
        Mockito.verify(first).setNext(second);
        Mockito.verify(second).setPrevious(first);
        Mockito.verify(second).setNext(next);
        Mockito.verify(next).setPrevious(second);
        Mockito.verifyNoMoreInteractions(first, second, next);
    }

    @Test
    public void fill_nextIsNull() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final NodeRule parent = Mockito.mock(NodeRule.class);
        final NodeRule previous = Mockito.mock(NodeRule.class);
        final NodeRule next = null;
        Mockito.when(rule.getParent()).thenReturn(parent);
        Mockito.when(rule.getPrevious()).thenReturn(previous);
        Mockito.when(rule.getNext()).thenReturn(next);

        final NodeRule first = Mockito.mock(NodeRule.class);
        final NodeRule second = Mockito.mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        Mockito.verify(first).setParent(parent);
        Mockito.verify(second).setParent(parent);
        Mockito.verify(first).setPrevious(previous);
        Mockito.verify(previous).setNext(first);
        Mockito.verify(first).setNext(second);
        Mockito.verify(second).setPrevious(first);
        Mockito.verify(second).setNext(next);
        Mockito.verifyNoMoreInteractions(previous, first, second);
    }
}
