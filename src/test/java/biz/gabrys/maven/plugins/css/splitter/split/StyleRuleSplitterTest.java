package biz.gabrys.maven.plugins.css.splitter.split;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class StyleRuleSplitterTest {

    @Test
    public void isSplittable2_ruleIsSplittable_returnsTrue() {
        final NeighborsManager neighborsManager = mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(true);

        final boolean splittable = splitter.isSplittable2(rule);

        assertThat(splittable).isTrue();
    }

    @Test
    public void isSplittable2_ruleIsUnsplittable_returnsFalse() {
        final NeighborsManager neighborsManager = mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(false);

        final boolean splittable = splitter.isSplittable2(rule);

        assertThat(splittable).isFalse();
    }

    @Test
    public void split2_ruleIsSplittable_returnsResultWithRuleParts() {
        final NeighborsManager neighborsManager = mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(true);

        final List<String> selectors = Arrays.asList("div", "p");
        when(rule.getSelectors()).thenReturn(selectors);

        final StyleProperty property1 = new StyleProperty("prop1", "val1");
        final StyleProperty property2 = new StyleProperty("prop2", "val2");
        final StyleProperty property3 = new StyleProperty("prop3", "val3");
        final List<StyleProperty> properties = Arrays.asList(property1, property2, property3);
        when(rule.getProperties()).thenReturn(properties);

        final SplitResult result = splitter.split2(rule, 1);

        final StyleRule firstRule = (StyleRule) result.getBefore();
        assertThat(firstRule).isNotNull();
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        assertThat(firstRuleSelectors).hasSameSizeAs(selectors);
        assertThat(selectors).containsAll(firstRuleSelectors);
        assertThat(firstRule.getProperties()).containsExactly(property1);

        final StyleRule secondRule = (StyleRule) result.getAfter();
        assertThat(secondRule).isNotNull();
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        assertThat(secondRuleSelectors).hasSameSizeAs(selectors);
        assertThat(selectors).containsAll(secondRuleSelectors);
        assertThat(secondRule.getProperties()).containsExactly(property2, property3);

        verify(neighborsManager).fill(rule, firstRule, secondRule);
        verifyNoMoreInteractions(neighborsManager);
    }
}
