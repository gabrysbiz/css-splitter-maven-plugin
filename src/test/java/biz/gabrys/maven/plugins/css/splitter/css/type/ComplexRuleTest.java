package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public final class ComplexRuleTest {

    @Test
    public void getLines_zeroRules() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<NodeRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String[] lines = rule.getLines();

        assertNotNull("Lines object should not be equal to null", lines);
        assertEquals("Lines quantity", 2, lines.length);
        assertEquals("Line no. 1", "@media sel1, sel2 {", lines[0]);
        assertEquals("Line no. 2", "}", lines[1]);
    }

    @Test
    public void getLines_twoRules() {
        final List<String> selectors = Arrays.asList("selector");

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = mock(NodeRule.class);
        when(child1.getLines()).thenReturn(new String[] { "rule {", "  prop: val;", "}" });
        rules.add(child1);
        final NodeRule child2 = mock(NodeRule.class);
        when(child2.getLines()).thenReturn(new String[] { "rule2 {", "  ", "}" });
        rules.add(child2);

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String[] lines = rule.getLines();

        assertNotNull("Lines object should not be equal to null", lines);
        assertEquals("Lines quantity", 8, lines.length);
        assertEquals("Line no. 1", "@media selector {", lines[0]);
        assertEquals("Line no. 2", "  rule {", lines[1]);
        assertEquals("Line no. 3", "    prop: val;", lines[2]);
        assertEquals("Line no. 4", "  }", lines[3]);
        assertEquals("Line no. 5", "  rule2 {", lines[4]);
        assertEquals("Line no. 6", "    ", lines[5]);
        assertEquals("Line no. 7", "  }", lines[6]);
        assertEquals("Line no. 8", "}", lines[7]);
    }

    @Test
    public void getSize2_zeroRules_returnsOne() {
        final List<String> selectors = Collections.emptyList();
        final List<NodeRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final int size = rule.getSize2();

        assertEquals(1, size);
    }

    @Test
    public void getSize2_twoRulesWithOneAndFourSize_returnsFive() {
        final List<String> selectors = Collections.emptyList();

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = mock(NodeRule.class);
        when(child1.getSize()).thenReturn(1);
        rules.add(child1);
        final NodeRule child2 = mock(NodeRule.class);
        when(child2.getSize()).thenReturn(4);
        rules.add(child2);

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final int size = rule.getSize2();

        assertEquals(5, size);
    }
}
