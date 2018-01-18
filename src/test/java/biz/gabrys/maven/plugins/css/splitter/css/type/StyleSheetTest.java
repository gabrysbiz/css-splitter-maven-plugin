package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public final class StyleSheetTest {

    @Test
    public void toString_sheetHasZeroRules() {
        final StyleSheet stylesheet = new StyleSheet(Collections.<NodeRule>emptyList());
        final String code = stylesheet.toString();
        assertThat(code).isEmpty();
    }

    @Test
    public void toString_sheetHasTwoRules() {
        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule rule1 = mock(NodeRule.class);
        when(rule1.toString()).thenReturn("abc\n");
        rules.add(rule1);
        final NodeRule rule2 = mock(NodeRule.class);
        when(rule2.toString()).thenReturn("def\n");
        rules.add(rule2);

        final StyleSheet stylesheet = new StyleSheet(rules);
        final String code = stylesheet.toString();

        assertThat(code).isEqualTo("abc\ndef\n");
    }

    @Test
    public void getSize_zeroRules_returnsZero() {
        final List<NodeRule> rules = Collections.emptyList();

        final StyleSheet stylesheet = new StyleSheet(rules);
        final int size = stylesheet.getSize();

        assertThat(size).isZero();
    }

    @Test
    public void getSize_twoRulesWithThreeAndTwoSize_returnsFive() {
        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final NodeRule child1 = mock(NodeRule.class);
        when(child1.getSize()).thenReturn(3);
        rules.add(child1);
        final NodeRule child2 = mock(NodeRule.class);
        when(child2.getSize()).thenReturn(2);
        rules.add(child2);

        final StyleSheet stylesheet = new StyleSheet(rules);
        final int size = stylesheet.getSize();

        assertThat(size).isEqualTo(5);
    }
}
