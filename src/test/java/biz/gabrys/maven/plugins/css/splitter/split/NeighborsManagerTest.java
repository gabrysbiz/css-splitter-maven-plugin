package biz.gabrys.maven.plugins.css.splitter.split;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class NeighborsManagerTest {

    @Test
    public void fill_fullNeighbors() {
        final NodeRule rule = mock(NodeRule.class);

        final NodeRule parent = mock(NodeRule.class);
        final NodeRule previous = mock(NodeRule.class);
        final NodeRule next = mock(NodeRule.class);
        when(rule.getParent()).thenReturn(parent);
        when(rule.getPrevious()).thenReturn(previous);
        when(rule.getNext()).thenReturn(next);

        final NodeRule first = mock(NodeRule.class);
        final NodeRule second = mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        verify(first).setParent(parent);
        verify(second).setParent(parent);
        verify(first).setPrevious(previous);
        verify(previous).setNext(first);
        verify(first).setNext(second);
        verify(second).setPrevious(first);
        verify(second).setNext(next);
        verify(next).setPrevious(second);
        verifyNoMoreInteractions(previous, first, second, next);
    }

    @Test
    public void fill_parentIsNull() {
        final NodeRule rule = mock(NodeRule.class);

        final NodeRule parent = null;
        final NodeRule previous = mock(NodeRule.class);
        final NodeRule next = mock(NodeRule.class);
        when(rule.getParent()).thenReturn(parent);
        when(rule.getPrevious()).thenReturn(previous);
        when(rule.getNext()).thenReturn(next);

        final NodeRule first = mock(NodeRule.class);
        final NodeRule second = mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        verify(first).setParent(parent);
        verify(second).setParent(parent);
        verify(first).setPrevious(previous);
        verify(previous).setNext(first);
        verify(first).setNext(second);
        verify(second).setPrevious(first);
        verify(second).setNext(next);
        verify(next).setPrevious(second);
        verifyNoMoreInteractions(previous, first, second, next);
    }

    @Test
    public void fill_previousIsNull() {
        final NodeRule rule = mock(NodeRule.class);

        final NodeRule parent = mock(NodeRule.class);
        final NodeRule previous = null;
        final NodeRule next = mock(NodeRule.class);
        when(rule.getParent()).thenReturn(parent);
        when(rule.getPrevious()).thenReturn(previous);
        when(rule.getNext()).thenReturn(next);

        final NodeRule first = mock(NodeRule.class);
        final NodeRule second = mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        verify(first).setParent(parent);
        verify(second).setParent(parent);
        verify(first).setPrevious(previous);
        verify(first).setNext(second);
        verify(second).setPrevious(first);
        verify(second).setNext(next);
        verify(next).setPrevious(second);
        verifyNoMoreInteractions(first, second, next);
    }

    @Test
    public void fill_nextIsNull() {
        final NodeRule rule = mock(NodeRule.class);

        final NodeRule parent = mock(NodeRule.class);
        final NodeRule previous = mock(NodeRule.class);
        final NodeRule next = null;
        when(rule.getParent()).thenReturn(parent);
        when(rule.getPrevious()).thenReturn(previous);
        when(rule.getNext()).thenReturn(next);

        final NodeRule first = mock(NodeRule.class);
        final NodeRule second = mock(NodeRule.class);
        final NeighborsManager neighborsManager = new NeighborsManager();
        neighborsManager.fill(rule, first, second);

        verify(first).setParent(parent);
        verify(second).setParent(parent);
        verify(first).setPrevious(previous);
        verify(previous).setNext(first);
        verify(first).setNext(second);
        verify(second).setPrevious(first);
        verify(second).setNext(next);
        verifyNoMoreInteractions(previous, first, second);
    }
}
