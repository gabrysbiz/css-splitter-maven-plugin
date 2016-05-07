package biz.gabrys.maven.plugins.css.splitter.css.counter;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class StyleSheetCounterTest {

    @Test
    public void count_styleSheetIsEmpty_returnsZero() {
        final RuleCounter internalCounter = Mockito.mock(RuleCounter.class);
        final StyleSheetCounter counter = new StyleSheetCounter(internalCounter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        Assert.assertEquals("Should return 0 for empty StyleSheet.", 0, counter.count(stylesheet));

        Mockito.verify(stylesheet).getRules();
        Mockito.verifyNoMoreInteractions(stylesheet);
        Mockito.verifyZeroInteractions(internalCounter);
    }

    @Test
    public void count_styleSheetIsNotEmpty_returnsCountedValue() {
        final RuleCounter internalCounter = Mockito.mock(RuleCounter.class);
        final StyleSheetCounter counter = new StyleSheetCounter(internalCounter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2));

        final int value1 = 3;
        Mockito.when(internalCounter.count(rule1)).thenReturn(value1);
        final int value2 = 5;
        Mockito.when(internalCounter.count(rule2)).thenReturn(value2);
        final int sum = value1 + value2;

        Assert.assertEquals("Should return sum for two rules.", sum, counter.count(stylesheet));

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(internalCounter).count(rule1);
        Mockito.verify(internalCounter).count(rule2);
        Mockito.verifyNoMoreInteractions(stylesheet, internalCounter);
        Mockito.verifyZeroInteractions(rule1, rule2);
    }
}
