package biz.gabrys.maven.plugins.css.splitter.counter;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class ComplexRuleCounterTest {

    @Test
    public void count_zeroChildren_returnsZero() {
        @SuppressWarnings("unchecked")
        final RuleCounter<StyleRule> styleCounter = Mockito.mock(RuleCounter.class);
        final ComplexRuleCounter counter = new ComplexRuleCounter(styleCounter);

        final ComplexRule rule = new ComplexRule("@media", Collections.<String>emptyList(), Collections.<StyleRule>emptyList());

        Assert.assertEquals("Styles count", 0, counter.count(rule));
        Mockito.verifyZeroInteractions(styleCounter);
    }

    @Test
    public void count_oneChildWithTwoProperties_returnsTwo() {
        @SuppressWarnings("unchecked")
        final RuleCounter<StyleRule> styleCounter = Mockito.mock(RuleCounter.class);
        final ComplexRuleCounter counter = new ComplexRuleCounter(styleCounter);

        final StyleRule child = Mockito.mock(StyleRule.class);
        Mockito.when(styleCounter.count(child)).thenReturn(2);

        final ComplexRule rule = new ComplexRule("@media", Collections.<String>emptyList(), Arrays.asList(child));

        Assert.assertEquals("Styles count", 2, counter.count(rule));
        Mockito.verify(styleCounter).count(child);
        Mockito.verifyNoMoreInteractions(styleCounter);
    }

    @Test
    public void count_twoChildrenWithOneAndFourProperties_returnsFive() {
        @SuppressWarnings("unchecked")
        final RuleCounter<StyleRule> styleCounter = Mockito.mock(RuleCounter.class);
        final ComplexRuleCounter counter = new ComplexRuleCounter(styleCounter);

        final StyleRule child1 = Mockito.mock(StyleRule.class);
        Mockito.when(styleCounter.count(child1)).thenReturn(1);

        final StyleRule child2 = Mockito.mock(StyleRule.class);
        Mockito.when(styleCounter.count(child2)).thenReturn(4);

        final ComplexRule rule = new ComplexRule("@media", Collections.<String>emptyList(), Arrays.asList(child1, child2));

        Assert.assertEquals("Styles count", 5, counter.count(rule));
        Mockito.verify(styleCounter).count(child1);
        Mockito.verify(styleCounter).count(child2);
        Mockito.verifyNoMoreInteractions(styleCounter);
    }
}
