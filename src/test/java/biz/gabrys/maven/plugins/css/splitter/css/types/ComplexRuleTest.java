package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class ComplexRuleTest {

    @Test
    public void getLines_zeroRules() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<NodeRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 2, lines.length);
        Assert.assertEquals("Line no. 1.", "@media sel1, sel2 {", lines[0]);
        Assert.assertEquals("Line no. 2.", "}", lines[1]);
    }

    @Test
    public void getLines_twoRules() {
        final List<String> selectors = Arrays.asList("selector");

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = Mockito.mock(NodeRule.class);
        Mockito.when(child1.getLines()).thenReturn(new String[] { "rule {", "  prop: val;", "}" });
        rules.add(child1);
        final NodeRule child2 = Mockito.mock(NodeRule.class);
        Mockito.when(child2.getLines()).thenReturn(new String[] { "rule2 {", "  ", "}" });
        rules.add(child2);

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 8, lines.length);
        Assert.assertEquals("Line no. 1.", "@media selector {", lines[0]);
        Assert.assertEquals("Line no. 2.", "  rule {", lines[1]);
        Assert.assertEquals("Line no. 3.", "    prop: val;", lines[2]);
        Assert.assertEquals("Line no. 4.", "  }", lines[3]);
        Assert.assertEquals("Line no. 5.", "  rule2 {", lines[4]);
        Assert.assertEquals("Line no. 6.", "    ", lines[5]);
        Assert.assertEquals("Line no. 7.", "  }", lines[6]);
        Assert.assertEquals("Line no. 8.", "}", lines[7]);
    }

    @Test
    public void getSize2_zeroRules_returnsZero() {
        final List<String> selectors = Collections.emptyList();
        final List<NodeRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final int size = rule.getSize2();

        Assert.assertEquals("Rule size.", 0, size);
    }

    @Test
    public void getSize2_twoRulesWithOneAndFourSize_returnsFive() {
        final List<String> selectors = Collections.emptyList();

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = Mockito.mock(NodeRule.class);
        Mockito.when(child1.getSize()).thenReturn(1);
        rules.add(child1);
        final NodeRule child2 = Mockito.mock(NodeRule.class);
        Mockito.when(child2.getSize()).thenReturn(4);
        rules.add(child2);

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final int size = rule.getSize2();

        Assert.assertEquals("Rule size.", 5, size);
    }
}
