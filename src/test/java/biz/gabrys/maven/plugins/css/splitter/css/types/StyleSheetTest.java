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
}
