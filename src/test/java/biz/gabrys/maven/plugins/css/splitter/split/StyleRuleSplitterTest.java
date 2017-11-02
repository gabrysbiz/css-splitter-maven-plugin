package biz.gabrys.maven.plugins.css.splitter.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

        assertTrue(splittable);
    }

    @Test
    public void isSplittable2_ruleIsUnsplittable_returnsFalse() {
        final NeighborsManager neighborsManager = mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(false);

        final boolean splittable = splitter.isSplittable2(rule);

        assertFalse(splittable);
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
        assertNotNull("First rule should not be equal to null", firstRule);
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        assertEquals("First rule selectors quantity", selectors.size(), firstRuleSelectors.size());
        assertTrue("First rule selectors list", selectors.containsAll(firstRuleSelectors));
        final List<StyleProperty> firstRuleProperties = firstRule.getProperties();
        assertEquals("First rule properties quantity", 1, firstRuleProperties.size());
        assertTrue("First rule contains first property", firstRuleProperties.contains(property1));

        final StyleRule secondRule = (StyleRule) result.getAfter();
        assertNotNull("Second rule should not be equal to null", secondRule);
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        assertEquals("Second rule selectors quantity", selectors.size(), secondRuleSelectors.size());
        assertTrue("Second rule selectors list", selectors.containsAll(secondRuleSelectors));
        final List<StyleProperty> secondRuleProperties = secondRule.getProperties();
        assertEquals("Second rule properties quantity", 2, secondRuleProperties.size());
        assertTrue("Second rule contains second property", secondRuleProperties.contains(property2));
        assertTrue("Second rule contains third property", secondRuleProperties.contains(property3));

        verify(neighborsManager).fill(rule, firstRule, secondRule);
        verifyNoMoreInteractions(neighborsManager);
    }
}
