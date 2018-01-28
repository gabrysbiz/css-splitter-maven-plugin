package biz.gabrys.maven.plugins.css.splitter.split;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(firstRule).isNotNull();
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        assertThat(firstRuleSelectors).hasSameSizeAs(selectors);
        assertThat(selectors).containsAll(firstRuleSelectors);
        assertThat(firstRule.getRules()).containsExactly(rule1);

        final ComplexRule secondRule = (ComplexRule) result.getAfter();
        assertThat(secondRule).isNotNull();
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        assertThat(secondRuleSelectors).hasSameSizeAs(selectors);
        assertThat(selectors).containsAll(secondRuleSelectors);
        assertThat(secondRule.getRules()).containsExactly(rule2);

        verify(neighborsManager).fill(rule, firstRule, secondRule);
        verifyNoMoreInteractions(neighborsManager);
    }
}
