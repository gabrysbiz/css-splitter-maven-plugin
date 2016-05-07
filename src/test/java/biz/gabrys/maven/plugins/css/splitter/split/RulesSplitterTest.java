package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class RulesSplitterTest {

    @Test
    public void split_twoRules_splitPointBetweenRules() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule2).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer<NodeRule> container = splitter.split(rules, 2);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 1, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 1, after.size());
        Assert.assertEquals("After rules first element", rule2, after.get(0));

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verifyNoMoreInteractions(rule1, rule2);
        Mockito.verifyZeroInteractions(ruleSplitter);
    }

    @Test
    public void split_fourRules_splitPointBetweenRules() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule2).size();
        final NodeRule rule3 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule3).size();
        final NodeRule rule4 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule4).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3, rule4);

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", rule2, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 2, after.size());
        Assert.assertEquals("After rules first element", rule3, after.get(0));
        Assert.assertEquals("After rules second element", rule4, after.get(1));

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verify(rule3).size();
        Mockito.verifyNoMoreInteractions(rule1, rule2, rule3);
        Mockito.verifyZeroInteractions(ruleSplitter, rule4);
    }

    @Test
    public void split_twoRules_splitRuleFromEndInTheMiddle() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(6).when(rule2).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        Mockito.when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = Mockito.mock(NodeRule.class);
        final NodeRule ruleAfter = Mockito.mock(NodeRule.class);
        Mockito.when(ruleSplitter.split(rule2, 2)).thenReturn(new SplitResult<NodeRule>(ruleBefore, ruleAfter));

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", ruleBefore, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 1, after.size());
        Assert.assertEquals("After rules first element", ruleAfter, after.get(0));

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verify(ruleSplitter).split(rule2, 2);
        Mockito.verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
    }

    @Test
    public void split_twoRules_splitRuleFromBeginInTheMiddle() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(6).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule2).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        Mockito.when(ruleSplitter.isSplittable(rule1)).thenReturn(true);
        final NodeRule ruleBefore = Mockito.mock(NodeRule.class);
        final NodeRule ruleAfter = Mockito.mock(NodeRule.class);
        Mockito.when(ruleSplitter.split(rule1, 4)).thenReturn(new SplitResult<NodeRule>(ruleBefore, ruleAfter));

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 1, before.size());
        Assert.assertEquals("Before rules first element", ruleBefore, before.get(0));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 2, after.size());
        Assert.assertEquals("After rules first element", ruleAfter, after.get(0));
        Assert.assertEquals("After rules second element", rule2, after.get(1));

        Mockito.verify(rule1).size();
        Mockito.verify(ruleSplitter).isSplittable(rule1);
        Mockito.verify(ruleSplitter).split(rule1, 4);
        Mockito.verifyNoMoreInteractions(rule1, ruleSplitter);
        Mockito.verifyZeroInteractions(rule2);
    }

    @Test
    public void split_threeRules_splitRuleFromMiddleInTheMiddle() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(4).when(rule2).size();
        final NodeRule rule3 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule3).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3);

        Mockito.when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = Mockito.mock(NodeRule.class);
        final NodeRule ruleAfter = Mockito.mock(NodeRule.class);
        Mockito.when(ruleSplitter.split(rule2, 2)).thenReturn(new SplitResult<NodeRule>(ruleBefore, ruleAfter));

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", ruleBefore, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 2, after.size());
        Assert.assertEquals("Before rules first element", ruleAfter, after.get(0));
        Assert.assertEquals("Before rules second element", rule3, after.get(1));

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verify(ruleSplitter).split(rule2, 2);
        Mockito.verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
    }

    @Test
    public void split_threeRules_splitPointIsInUnsplittableRule() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(4).when(rule2).size();
        final NodeRule rule3 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(2).when(rule3).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3);

        Mockito.when(ruleSplitter.isSplittable(rule2)).thenReturn(false);

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 1, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 2, after.size());
        Assert.assertEquals("Before rules first element", rule2, after.get(0));
        Assert.assertEquals("Before rules second element", rule3, after.get(1));

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verifyNoMoreInteractions(rule1, rule2, ruleSplitter);
        Mockito.verifyZeroInteractions(rule3);
    }

    @Test
    public void split_twoRulesWhoseDoNotRequireSplit_afterCollectionIsEmpty() {
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(ruleSplitter);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(1).when(rule1).size();
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.doReturn(1).when(rule2).size();
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", rule2, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertTrue("After rules should be empty", after.isEmpty());

        Mockito.verify(rule1).size();
        Mockito.verify(rule2).size();
        Mockito.verifyNoMoreInteractions(rule1, rule2);
        Mockito.verifyZeroInteractions(ruleSplitter);
    }
}
