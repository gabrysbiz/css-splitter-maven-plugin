package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class ComplexRuleSplitterTest {

    @Test
    public void split2_ruleWithTwoSplittableSubrules_returnsResultWithTwoComplexRules() {
        final RulesSplitter rulesSplitter = Mockito.mock(RulesSplitter.class);
        final NeighborsManager neighborsManager = Mockito.mock(NeighborsManager.class);
        final ComplexRuleSplitter complexSplitter = Mockito.spy(new ComplexRuleSplitter(rulesSplitter, neighborsManager));

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        final String type = "@type";
        Mockito.when(rule.getType()).thenReturn(type);

        final List<String> selectors = Arrays.asList("div", "p");
        Mockito.when(rule.getSelectors()).thenReturn(selectors);

        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);
        Mockito.when(rule.getRules()).thenReturn(new ArrayList<NodeRule>(rules));

        final RulesContainer container = new RulesContainer();
        container.before.add(rule1);
        container.after.add(rule2);
        Mockito.when(rulesSplitter.split(rules, 2)).thenReturn(container);

        final SplitResult result = complexSplitter.split2(rule, 2);

        final ComplexRule firstRule = (ComplexRule) result.getBefore();
        Assert.assertNotNull("First rule should not be equal to null", firstRule);
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        Assert.assertEquals("First rule selectors quantity", selectors.size(), firstRuleSelectors.size());
        Assert.assertTrue("First rule selectors list", selectors.containsAll(firstRuleSelectors));
        final List<NodeRule> firstRuleChildren = firstRule.getRules();
        Assert.assertEquals("First children rules list size", 1, firstRuleChildren.size());
        Assert.assertEquals("First children rules first element", rule1, firstRuleChildren.get(0));

        final ComplexRule secondRule = (ComplexRule) result.getAfter();
        Assert.assertNotNull("Second rule should not be equal to null", secondRule);
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        Assert.assertEquals("Second rule selectors quantity", selectors.size(), secondRuleSelectors.size());
        Assert.assertTrue("Second rule selectors list", selectors.containsAll(secondRuleSelectors));
        final List<NodeRule> secondRuleChildren = secondRule.getRules();
        Assert.assertEquals("Second children rules list size", 1, secondRuleChildren.size());
        Assert.assertEquals("Second children rules first element", rule2, secondRuleChildren.get(0));

        Mockito.verify(neighborsManager).fill(rule, firstRule, secondRule);
        Mockito.verifyNoMoreInteractions(neighborsManager);
    }
}
