package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class StyleSheetTest {

    @Test
    public void toString_sheetHasZeroRules() {
        final StyleSheet stylesheet = new StyleSheet(Collections.<NodeRule>emptyList());
        final String code = stylesheet.toString();
        Assert.assertEquals("CSS code", "", code);
    }

    @Test
    public void toString_sheetHasTwoRules() {
        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        Mockito.when(rule1.toString()).thenReturn("abc\n");
        rules.add(rule1);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.when(rule2.toString()).thenReturn("def\n");
        rules.add(rule2);

        final StyleSheet stylesheet = new StyleSheet(rules);
        final String code = stylesheet.toString();
        Assert.assertEquals("CSS code", "abc\ndef\n", code);
    }

    // @Test
    // public void count_styleSheetIsEmpty_returnsZero() {
    // final StyleSheetCounter counter = new StyleSheetCounter();
    //
    // final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
    // Mockito.when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());
    //
    // Assert.assertEquals("Should return 0 for empty StyleSheet.", 0, counter.count(stylesheet));
    //
    // Mockito.verify(stylesheet).getRules();
    // Mockito.verifyNoMoreInteractions(stylesheet);
    // }
    //
    // @Test
    // public void count_styleSheetIsNotEmpty_returnsCountedValue() {
    // final StyleSheetCounter counter = new StyleSheetCounter();
    //
    // final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
    // final NodeRule rule1 = Mockito.mock(NodeRule.class);
    // final NodeRule rule2 = Mockito.mock(NodeRule.class);
    // Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2));
    //
    // final int value1 = 3;
    // Mockito.when(rule1.size()).thenReturn(value1);
    // final int value2 = 5;
    // Mockito.when(rule2.size()).thenReturn(value2);
    // final int sum = value1 + value2;
    //
    // Assert.assertEquals("Should return sum for two rules.", sum, counter.count(stylesheet));
    //
    // Mockito.verify(stylesheet).getRules();
    // Mockito.verify(rule1).size();
    // Mockito.verify(rule2).size();
    // Mockito.verifyNoMoreInteractions(stylesheet, rule1, rule2);
    // }
}
