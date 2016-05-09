package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class StyleRuleSplitterTest {

    @Test
    public void isSplittable2_ruleIsSplittable_returnsTrue() {
        final NeighborsManager neighborsManager = Mockito.mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(true);

        final boolean splittable = splitter.isSplittable2(rule);

        Assert.assertTrue("Should return true for splittable rule", splittable);
    }

    @Test
    public void isSplittable2_ruleIsUnsplittable_returnsFalse() {
        final NeighborsManager neighborsManager = Mockito.mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);

        final boolean splittable = splitter.isSplittable2(rule);

        Assert.assertFalse("Should return false for unsplittable rule", splittable);
    }

    @Test
    public void split2_ruleIsSplittable_returnsResultWithRuleParts() {
        final NeighborsManager neighborsManager = Mockito.mock(NeighborsManager.class);
        final StyleRuleSplitter splitter = new StyleRuleSplitter(neighborsManager);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(true);

        final List<String> selectors = Arrays.asList("div", "p");
        Mockito.when(rule.getSelectors()).thenReturn(selectors);

        final StyleProperty property1 = new StyleProperty("prop1", "val1");
        final StyleProperty property2 = new StyleProperty("prop2", "val2");
        final StyleProperty property3 = new StyleProperty("prop3", "val3");
        final List<StyleProperty> properties = Arrays.asList(property1, property2, property3);
        Mockito.when(rule.getProperties()).thenReturn(properties);

        final SplitResult result = splitter.split2(rule, 1);

        final StyleRule firstRule = (StyleRule) result.getBefore();
        Assert.assertNotNull("First rule should not be equal to null", firstRule);
        final List<String> firstRuleSelectors = firstRule.getSelectors();
        Assert.assertEquals("First rule selectors quantity", selectors.size(), firstRuleSelectors.size());
        Assert.assertTrue("First rule selectors list", selectors.containsAll(firstRuleSelectors));
        final List<StyleProperty> firstRuleProperties = firstRule.getProperties();
        Assert.assertEquals("First rule properties quantity", 1, firstRuleProperties.size());
        Assert.assertTrue("First rule contains first property", firstRuleProperties.contains(property1));

        final StyleRule secondRule = (StyleRule) result.getAfter();
        Assert.assertNotNull("Second rule should not be equal to null", secondRule);
        final List<String> secondRuleSelectors = secondRule.getSelectors();
        Assert.assertEquals("Second rule selectors quantity", selectors.size(), secondRuleSelectors.size());
        Assert.assertTrue("Second rule selectors list", selectors.containsAll(secondRuleSelectors));
        final List<StyleProperty> secondRuleProperties = secondRule.getProperties();
        Assert.assertEquals("Second rule properties quantity", 2, secondRuleProperties.size());
        Assert.assertTrue("Second rule contains second property", secondRuleProperties.contains(property2));
        Assert.assertTrue("Second rule contains third property", secondRuleProperties.contains(property3));

        Mockito.verify(neighborsManager).fill(rule, firstRule, secondRule);
        Mockito.verifyNoMoreInteractions(neighborsManager);
    }
}
