package biz.gabrys.maven.plugins.css.splitter.css.counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class AnyRuleCounterTest {

    @Test
    public void count_ruleIsSupported_returnsCountedValue() {
        final NodeRule rule = Mockito.mock(NodeRule.class);

        final List<RuleCounter> counters = new ArrayList<RuleCounter>();
        final RuleCounter unusedCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(unusedCounter.isSupportedType(rule)).thenReturn(false);
        counters.add(unusedCounter);
        final RuleCounter styleCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(styleCounter.isSupportedType(rule)).thenReturn(true);
        final int count = 2;
        Mockito.when(styleCounter.count(rule)).thenReturn(count);
        counters.add(styleCounter);

        Assert.assertEquals("Counted value", count, new AnyRuleCounter(counters).count(rule));

        Mockito.verify(unusedCounter).isSupportedType(rule);
        Mockito.verify(styleCounter).isSupportedType(rule);
        Mockito.verify(styleCounter).count(rule);
        Mockito.verifyNoMoreInteractions(unusedCounter, styleCounter);
    }

    @Test
    public void count_ruleIsNotSupported_returnsZero() {
        final StyleRule rule = new StyleRule(Collections.<String>emptyList(), Collections.<StyleProperty>emptyList());

        final List<RuleCounter> counters = new ArrayList<RuleCounter>();
        final RuleCounter unusedCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(unusedCounter.isSupportedType(rule)).thenReturn(false);
        counters.add(unusedCounter);

        Assert.assertEquals("Counted value", 0, new AnyRuleCounter(counters).count(rule));

        Mockito.verify(unusedCounter).isSupportedType(rule);
        Mockito.verifyNoMoreInteractions(unusedCounter);
    }
}
