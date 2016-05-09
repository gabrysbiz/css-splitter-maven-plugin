package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class MultipleRuleSplitterTest {

    @Test
    public void isSplittable_atLeastOneInternalSplitterReturnTrue_returnsTrue() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();
        final RuleSplitter splitter1 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);
        final RuleSplitter splitter2 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter2.isSplittable(rule)).thenReturn(true);
        splitters.add(splitter2);
        final RuleSplitter splitter3 = Mockito.mock(RuleSplitter.class);
        splitters.add(splitter3);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final boolean splittable = splitter.isSplittable(rule);

        Assert.assertTrue("Should return true", splittable);
        Mockito.verify(splitter1).isSplittable(rule);
        Mockito.verify(splitter2).isSplittable(rule);
        Mockito.verifyNoMoreInteractions(splitter1, splitter2);
        Mockito.verifyZeroInteractions(splitter3);
    }

    @Test
    public void isSplittable_noOneInternalSplitterSupportsSplit_returnsFalse() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();
        final RuleSplitter splitter1 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);
        final RuleSplitter splitter2 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter2.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter2);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final boolean splittable = splitter.isSplittable(rule);

        Assert.assertFalse("Should return false", splittable);
        Mockito.verify(splitter1).isSplittable(rule);
        Mockito.verify(splitter2).isSplittable(rule);
        Mockito.verifyNoMoreInteractions(splitter1, splitter2);
    }

    @Test
    public void split_atLeastOneInternalSplitterSupportSplit_splitsRule() {
        final NodeRule rule = Mockito.mock(NodeRule.class);
        final int splitAfter = 1;

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();

        final RuleSplitter splitter1 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);

        final RuleSplitter splitter2 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter2.isSplittable(rule)).thenReturn(true);
        final SplitResult result = Mockito.mock(SplitResult.class);
        Mockito.when(splitter2.split(rule, splitAfter)).thenReturn(result);
        splitters.add(splitter2);

        final RuleSplitter splitter3 = Mockito.mock(RuleSplitter.class);
        splitters.add(splitter3);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        final SplitResult returnedResult = splitter.split(rule, splitAfter);

        Assert.assertEquals("Returned result", result, returnedResult);
        Mockito.verify(splitter1).isSplittable(rule);
        Mockito.verify(splitter2).isSplittable(rule);
        Mockito.verify(splitter2).split(rule, splitAfter);
        Mockito.verifyNoMoreInteractions(splitter1, splitter2);
        Mockito.verifyZeroInteractions(splitter3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_noOneInternalSplitterSupportsSplit_throwsException() {
        final NodeRule rule = Mockito.mock(NodeRule.class);
        final int splitAfter = 1;

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();

        final RuleSplitter splitter1 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter1.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter1);

        final RuleSplitter splitter2 = Mockito.mock(RuleSplitter.class);
        Mockito.when(splitter2.isSplittable(rule)).thenReturn(false);
        splitters.add(splitter2);

        final MultipleRuleSplitter splitter = new MultipleRuleSplitter(splitters);

        splitter.split(rule, splitAfter);
    }
}
