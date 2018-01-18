package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;
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

        assertThat(lines).hasSize(2);
        assertThat(lines[0]).isEqualTo("@media sel1, sel2 {");
        assertThat(lines[1]).isEqualTo("}");
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

        assertThat(lines).hasSize(8);
        assertThat(lines[0]).isEqualTo("@media selector {");
        assertThat(lines[1]).isEqualTo("  rule {");
        assertThat(lines[2]).isEqualTo("    prop: val;");
        assertThat(lines[3]).isEqualTo("  }");
        assertThat(lines[4]).isEqualTo("  rule2 {");
        assertThat(lines[5]).isEqualTo("    ");
        assertThat(lines[6]).isEqualTo("  }");
        assertThat(lines[7]).isEqualTo("}");
    }

    @Test
    public void getSize2_zeroRules_returnsOne() {
        final List<String> selectors = Collections.emptyList();
        final List<NodeRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final int size = rule.getSize2();

        assertThat(size).isEqualTo(1);
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

        assertThat(size).isEqualTo(5);
    }
}
