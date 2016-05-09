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
        Assert.assertEquals("Stylesheet code.", "", code);
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
        Assert.assertEquals("Stylesheet code.", "abc\ndef\n", code);
    }

    @Test
    public void getSize_zeroRules_returnsZero() {
        final List<NodeRule> rules = Collections.emptyList();

        final StyleSheet stylesheet = new StyleSheet(rules);
        final int size = stylesheet.getSize();

        Assert.assertEquals("Stylesheet size.", 0, size);
    }

    @Test
    public void getSize_twoRulesWithThreeAndTwoSize_returnsFive() {
        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = Mockito.mock(NodeRule.class);
        Mockito.when(child1.getSize()).thenReturn(3);
        rules.add(child1);
        final NodeRule child2 = Mockito.mock(NodeRule.class);
        Mockito.when(child2.getSize()).thenReturn(2);
        rules.add(child2);

        final StyleSheet stylesheet = new StyleSheet(rules);
        final int size = stylesheet.getSize();

        Assert.assertEquals("Stylesheet size.", 5, size);
    }
}
