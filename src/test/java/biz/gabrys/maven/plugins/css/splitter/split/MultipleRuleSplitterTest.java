package biz.gabrys.maven.plugins.css.splitter.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class MultipleRuleSplitterTest {

    @Test
    public void isSplittable_atLeastOneInternalSplitterReturnTrue_returnsTrue() {
        final NodeRule rule = mock(NodeRule.class);

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();
        final RuleSplitter splitter1 = mock(RuleSplitter.class);
        when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);
        final RuleSplitter splitter2 = mock(RuleSplitter.class);
        when(splitter2.isSplittable(rule)).thenReturn(true);
        splitters.add(splitter2);
        final RuleSplitter splitter3 = mock(RuleSplitter.class);
        splitters.add(splitter3);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final boolean splittable = splitter.isSplittable(rule);

        assertTrue(splittable);
        verify(splitter1).isSplittable(rule);
        verify(splitter2).isSplittable(rule);
        verifyNoMoreInteractions(splitter1, splitter2);
        verifyZeroInteractions(splitter3);
    }

    @Test
    public void isSplittable_noOneInternalSplitterSupportsSplit_returnsFalse() {
        final NodeRule rule = mock(NodeRule.class);

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();
        final RuleSplitter splitter1 = mock(RuleSplitter.class);
        when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);
        final RuleSplitter splitter2 = mock(RuleSplitter.class);
        when(splitter2.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter2);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final boolean splittable = splitter.isSplittable(rule);

        assertFalse(splittable);
        verify(splitter1).isSplittable(rule);
        verify(splitter2).isSplittable(rule);
        verifyNoMoreInteractions(splitter1, splitter2);
    }

    @Test
    public void split_atLeastOneInternalSplitterSupportSplit_splitsRule() {
        final NodeRule rule = mock(NodeRule.class);
        final int splitAfter = 1;

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();

        final RuleSplitter splitter1 = mock(RuleSplitter.class);
        when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);

        final RuleSplitter splitter2 = mock(RuleSplitter.class);
        when(splitter2.isSplittable(rule)).thenReturn(true);
        final SplitResult result = mock(SplitResult.class);
        when(splitter2.split(rule, splitAfter)).thenReturn(result);
        splitters.add(splitter2);

        final RuleSplitter splitter3 = mock(RuleSplitter.class);
        splitters.add(splitter3);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final SplitResult returnedResult = splitter.split(rule, splitAfter);

        assertEquals(result, returnedResult);
        verify(splitter1).isSplittable(rule);
        verify(splitter2).isSplittable(rule);
        verify(splitter2).split(rule, splitAfter);
        verifyNoMoreInteractions(splitter1, splitter2);
        verifyZeroInteractions(splitter3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_noOneInternalSplitterSupportsSplit_throwsException() {
        final NodeRule rule = mock(NodeRule.class);
        final int splitAfter = 1;

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();

        final RuleSplitter splitter1 = mock(RuleSplitter.class);
        when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);

        final RuleSplitter splitter2 = mock(RuleSplitter.class);
        when(splitter2.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter2);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        splitter.split(rule, splitAfter);
    }
}
