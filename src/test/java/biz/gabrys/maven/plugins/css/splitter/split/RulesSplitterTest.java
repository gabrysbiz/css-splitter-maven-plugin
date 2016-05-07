package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRuleImpl;

public final class RulesSplitterTest {

    @Test
    public void split_twoRules_splitPointBetweenRules() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(2);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(2);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer<NodeRule> container = splitter.split(rules, 2);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 1, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 1, after.size());
        Assert.assertEquals("After rules first element", rule2, after.get(0));

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(ruleSplitter);
    }

    @Test
    public void split_fourRules_splitPointBetweenRules() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(2);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(2);
        final NodeRule rule3 = new NodeRuleImpl();
        Mockito.when(counter.count(rule3)).thenReturn(2);
        final NodeRule rule4 = new NodeRuleImpl();
        Mockito.when(counter.count(rule4)).thenReturn(2);
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

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verify(counter).count(rule3);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(ruleSplitter);
    }

    @Test
    public void split_twoRules_splitRuleFromEndInTheMiddle() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(2);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(6);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        Mockito.when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = new NodeRuleImpl();
        final NodeRule ruleAfter = new NodeRuleImpl();
        Mockito.when(ruleSplitter.split(rule2, 2)).thenReturn(new SplitResult<NodeRule>(ruleBefore, ruleAfter));

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", ruleBefore, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 1, after.size());
        Assert.assertEquals("After rules first element", ruleAfter, after.get(0));

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verify(ruleSplitter).split(rule2, 2);
        Mockito.verifyNoMoreInteractions(counter, ruleSplitter);
    }

    @Test
    public void split_twoRules_splitRuleFromBeginInTheMiddle() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(6);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(2);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        Mockito.when(ruleSplitter.isSplittable(rule1)).thenReturn(true);
        final NodeRule ruleBefore = new NodeRuleImpl();
        final NodeRule ruleAfter = new NodeRuleImpl();
        Mockito.when(ruleSplitter.split(rule1, 4)).thenReturn(new SplitResult<NodeRule>(ruleBefore, ruleAfter));

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 1, before.size());
        Assert.assertEquals("Before rules first element", ruleBefore, before.get(0));

        final List<NodeRule> after = container.after;
        Assert.assertEquals("After rules quantity", 2, after.size());
        Assert.assertEquals("After rules first element", ruleAfter, after.get(0));
        Assert.assertEquals("After rules second element", rule2, after.get(1));

        Mockito.verify(counter).count(rule1);
        Mockito.verify(ruleSplitter).isSplittable(rule1);
        Mockito.verify(ruleSplitter).split(rule1, 4);
        Mockito.verifyNoMoreInteractions(counter, ruleSplitter);
    }

    @Test
    public void split_threeRules_splitRuleFromMiddleInTheMiddle() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(2);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(4);
        final NodeRule rule3 = new NodeRuleImpl();
        Mockito.when(counter.count(rule3)).thenReturn(2);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2, rule3);

        Mockito.when(ruleSplitter.isSplittable(rule2)).thenReturn(true);
        final NodeRule ruleBefore = new NodeRuleImpl();
        final NodeRule ruleAfter = new NodeRuleImpl();
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

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verify(ruleSplitter).split(rule2, 2);
        Mockito.verifyNoMoreInteractions(counter, ruleSplitter);
    }

    @Test
    public void split_threeRules_splitPointIsInUnsplittableRule() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(2);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(4);
        final NodeRule rule3 = new NodeRuleImpl();
        Mockito.when(counter.count(rule3)).thenReturn(2);
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

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verify(ruleSplitter).isSplittable(rule2);
        Mockito.verifyNoMoreInteractions(counter, ruleSplitter);
    }

    @Test
    public void split_twoRulesWhoseDoNotRequireSplit_afterCollectionIsEmpty() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        @SuppressWarnings("unchecked")
        final RuleSplitter<NodeRule> ruleSplitter = Mockito.mock(RuleSplitter.class);
        final RulesSplitter<NodeRule> splitter = new RulesSplitter<NodeRule>(counter, ruleSplitter);

        final NodeRule rule1 = new NodeRuleImpl();
        Mockito.when(counter.count(rule1)).thenReturn(1);
        final NodeRule rule2 = new NodeRuleImpl();
        Mockito.when(counter.count(rule2)).thenReturn(1);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);

        final RulesContainer<NodeRule> container = splitter.split(rules, 4);

        final List<NodeRule> before = container.before;
        Assert.assertEquals("Before rules quantity", 2, before.size());
        Assert.assertEquals("Before rules first element", rule1, before.get(0));
        Assert.assertEquals("Before rules second element", rule2, before.get(1));

        final List<NodeRule> after = container.after;
        Assert.assertTrue("After rules should be empty", after.isEmpty());

        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(ruleSplitter);
    }
}
