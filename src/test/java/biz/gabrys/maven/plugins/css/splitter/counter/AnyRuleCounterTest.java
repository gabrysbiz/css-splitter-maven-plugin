package biz.gabrys.maven.plugins.css.splitter.counter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class AnyRuleCounterTest {

    @Test
    public void count_classSupported_returnsCountedValue() {
        final StyleRule rule = new StyleRule(Collections.<String>emptyList(), Collections.<StyleProperty>emptyList());

        final Collection<RuleCounter<?>> counters = new ArrayList<RuleCounter<?>>();
        @SuppressWarnings("unchecked")
        final RuleCounter<NodeRule> unusedCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(unusedCounter.getSupportedType()).thenReturn(NodeRule.class);
        counters.add(unusedCounter);
        @SuppressWarnings("unchecked")
        final RuleCounter<StyleRule> styleCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(styleCounter.getSupportedType()).thenReturn(StyleRule.class);
        final int count = 2;
        Mockito.when(styleCounter.count(rule)).thenReturn(count);
        counters.add(styleCounter);

        Assert.assertEquals("Counted value", count, new AnyRuleCounter(counters).count(rule));

        Mockito.verify(unusedCounter).getSupportedType();
        Mockito.verify(styleCounter).getSupportedType();
        Mockito.verify(styleCounter).count(rule);
        Mockito.verifyNoMoreInteractions(unusedCounter, styleCounter);
    }

    @Test
    public void count_classNotSupported_returnsZero() {
        final StyleRule rule = new StyleRule(Collections.<String>emptyList(), Collections.<StyleProperty>emptyList());

        final Collection<RuleCounter<?>> counters = new ArrayList<RuleCounter<?>>();
        @SuppressWarnings("unchecked")
        final RuleCounter<NodeRule> unusedCounter = Mockito.mock(RuleCounter.class);
        Mockito.when(unusedCounter.getSupportedType()).thenReturn(NodeRule.class);
        counters.add(unusedCounter);

        Assert.assertEquals("Counted value", 0, new AnyRuleCounter(counters).count(rule));

        Mockito.verify(unusedCounter).getSupportedType();
        Mockito.verifyNoMoreInteractions(unusedCounter);
    }
}
