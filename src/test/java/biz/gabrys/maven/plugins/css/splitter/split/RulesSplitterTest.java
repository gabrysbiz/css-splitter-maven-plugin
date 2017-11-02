package biz.gabrys.maven.plugins.css.splitter.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class RulesSplitterTest {

    @Test
    public void split_twoRules_splitPointBetweenRules() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(2).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(2).when(rule2).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer container = splitter.split(rules, 2);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 1, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 1, after.size());
        assertEquals("After rules first element", rule2, after.get(0));

        verify(rule1).getSize();
        verify(rule2).getSize();
        verifyNoMoreInteractions(rule1, rule2);
        verifyZeroInteractions(ruleSplitter);
    }

    @Test
    public void split_fourRules_splitPointBetweenRules() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(2).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(2).when(rule2).getSize();
        final NodeRule rule3 = mock(NodeRule.class);
        doReturn(2).when(rule3).getSize();
        final NodeRule rule4 = mock(NodeRule.class);
        doReturn(2).when(rule4).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3, rule4);

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 2, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));
        assertEquals("Before rules second element", rule2, before.get(1));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 2, after.size());
        assertEquals("After rules first element", rule3, after.get(0));
        assertEquals("After rules second element", rule4, after.get(1));

        verify(rule1).getSize();
        verify(rule2).getSize();
        verify(rule3).getSize();
        verifyNoMoreInteractions(rule1, rule2, rule3);
        verifyZeroInteractions(ruleSplitter, rule4);
    }

    @Test
    public void split_twoRules_splitRuleFromEndInTheMiddle() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(2).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(6).when(rule2).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = mock(NodeRule.class);
        final NodeRule ruleAfter = mock(NodeRule.class);
        when(ruleSplitter.split(rule2, 2)).thenReturn(new SplitResult(ruleBefore, ruleAfter));

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 2, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));
        assertEquals("Before rules second element", ruleBefore, before.get(1));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 1, after.size());
        assertEquals("After rules first element", ruleAfter, after.get(0));

        verify(rule1).getSize();
        verify(rule2).getSize();
        verify(ruleSplitter).isSplittable(rule2);
        verify(ruleSplitter).split(rule2, 2);
        verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
    }

    @Test
    public void split_twoRules_splitRuleFromBeginInTheMiddle() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(6).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(2).when(rule2).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        when(ruleSplitter.isSplittable(rule1)).thenReturn(true);
        final NodeRule ruleBefore = mock(NodeRule.class);
        final NodeRule ruleAfter = mock(NodeRule.class);
        when(ruleSplitter.split(rule1, 4)).thenReturn(new SplitResult(ruleBefore, ruleAfter));

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 1, before.size());
        assertEquals("Before rules first element", ruleBefore, before.get(0));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 2, after.size());
        assertEquals("After rules first element", ruleAfter, after.get(0));
        assertEquals("After rules second element", rule2, after.get(1));

        verify(rule1).getSize();
        verify(ruleSplitter).isSplittable(rule1);
        verify(ruleSplitter).split(rule1, 4);
        verifyNoMoreInteractions(rule1, ruleSplitter);
        verifyZeroInteractions(rule2);
    }

    @Test
    public void split_threeRules_splitRuleFromMiddleInTheMiddle() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(2).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(4).when(rule2).getSize();
        final NodeRule rule3 = mock(NodeRule.class);
        doReturn(2).when(rule3).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3);

        when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = mock(NodeRule.class);
        final NodeRule ruleAfter = mock(NodeRule.class);
        when(ruleSplitter.split(rule2, 2)).thenReturn(new SplitResult(ruleBefore, ruleAfter));

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 2, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));
        assertEquals("Before rules second element", ruleBefore, before.get(1));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 2, after.size());
        assertEquals("Before rules first element", ruleAfter, after.get(0));
        assertEquals("Before rules second element", rule3, after.get(1));

        verify(rule1).getSize();
        verify(rule2).getSize();
        verify(ruleSplitter).isSplittable(rule2);
        verify(ruleSplitter).split(rule2, 2);
        verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
    }

    @Test
    public void split_threeRules_splitPointIsInUnsplittableRule() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(2).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(4).when(rule2).getSize();
        final NodeRule rule3 = mock(NodeRule.class);
        doReturn(2).when(rule3).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3);

        when(ruleSplitter.isSplittable(rule2)).thenReturn(false);

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 1, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));

        final List<NodeRule> after = container.after;
        assertEquals("After rules quantity", 2, after.size());
        assertEquals("Before rules first element", rule2, after.get(0));
        assertEquals("Before rules second element", rule3, after.get(1));

        verify(rule1).getSize();
        verify(rule2).getSize();
        verify(ruleSplitter).isSplittable(rule2);
        verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
        verifyZeroInteractions(rule3);
    }

    @Test
    public void split_twoRulesWhoseDoNotRequireSplit_afterCollectionIsEmpty() {
        final RuleSplitter ruleSplitter = mock(RuleSplitter.class);
        final RulesSplitter splitter = new RulesSplitter(ruleSplitter);

        final NodeRule rule1 = mock(NodeRule.class);
        doReturn(1).when(rule1).getSize();
        final NodeRule rule2 = mock(NodeRule.class);
        doReturn(1).when(rule2).getSize();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        assertEquals("Before rules quantity", 2, before.size());
        assertEquals("Before rules first element", rule1, before.get(0));
        assertEquals("Before rules second element", rule2, before.get(1));

        final List<NodeRule> after = container.after;
        assertTrue("After rules should be empty", after.isEmpty());

        verify(rule1).getSize();
        verify(rule2).getSize();
        verifyNoMoreInteractions(rule1, rule2);
        verifyZeroInteractions(ruleSplitter);
    }
}
