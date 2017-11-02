package biz.gabrys.maven.plugins.css.splitter.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class ComplexRuleSplitterTest {

    @Test
    public void split2_ruleWithTwoSplittableSubrules_returnsResultWithTwoComplexRules() {
        final RulesSplitter rulesSplitter = mock(RulesSplitter.class);
        final NeighborsManager neighborsManager = mock(NeighborsManager.class);
        final ComplexRuleSplitter complexSplitter = spy(new ComplexRuleSplitter(rulesSplitter, neighborsManager));

        final ComplexRule rule = mock(ComplexRule.class);
        final String type = "@type";
        when(rule.getType()).thenReturn(type);

        final List<String> selectors = Arrays.asList("div", "p");
        when(rule.getSelectors()).thenReturn(selectors);

        final NodeRule rule1 = mock(NodeRule.class);
        final NodeRule rule2 = mock(NodeRule.class);
        final List<NodeRule> rules = Arrays.asList(rule1, rule2);
        when(rule.getRules()).thenReturn(new ArrayList<NodeRule>(rules));

        final RulesContainer container = new RulesContainer();
        container.before.add(rule1);
        container.after.add(rule2);
        when(rulesSplitter.split(rules, 2)).thenReturn(container);

        final SplitResult result = complexSplitter.split2(rule, 2);

        final ComplexRule firstRule = (ComplexRule) result.getBefore();
        assertNotNull("First rule should not be equal to null", firstRule);
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        assertEquals("First rule selectors quantity", selectors.size(), firstRuleSelectors.size());
        assertTrue("First rule selectors list", selectors.containsAll(firstRuleSelectors));
        final List<NodeRule> firstRuleChildren = firstRule.getRules();
        assertEquals("First children rules list size", 1, firstRuleChildren.size());
        assertEquals("First children rules first element", rule1, firstRuleChildren.get(0));

        final ComplexRule secondRule = (ComplexRule) result.getAfter();
        assertNotNull("Second rule should not be equal to null", secondRule);
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        assertEquals("Second rule selectors quantity", selectors.size(), secondRuleSelectors.size());
        assertTrue("Second rule selectors list", selectors.containsAll(secondRuleSelectors));
        final List<NodeRule> secondRuleChildren = secondRule.getRules();
        assertEquals("Second children rules list size", 1, secondRuleChildren.size());
        assertEquals("Second children rules first element", rule2, secondRuleChildren.get(0));

        verify(neighborsManager).fill(rule, firstRule, secondRule);
        verifyNoMoreInteractions(neighborsManager);
    }
}
