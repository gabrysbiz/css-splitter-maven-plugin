package biz.gabrys.maven.plugins.css.splitter.split;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public class StyleSheetSplliterTest {

    @Test
    public void split_styleSheetContainsZeroRules_returnOneEmptyStyleSheet() {
        final RulesSplitter rulesSplitter = mock(RulesSplitter.class);
        final StyleSheetSplliter splliter = new StyleSheetSplliter(0, rulesSplitter);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        final List<StyleSheet> sheets = splliter.split(stylesheet);

        assertThat(sheets).containsExactly(stylesheet);
    }

    @Test
    public void split_styleSheetWillBeSplitedToThreeDocuments_returnsThreeDocuments() {
        final int limit = 1;
        final RulesSplitter rulesSplitter = mock(RulesSplitter.class);
        final StyleSheetSplliter splliter = new StyleSheetSplliter(limit, rulesSplitter);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule1 = new SupportedTestNodeRule();
        final NodeRule rule2 = new SupportedTestNodeRule();
        final NodeRule rule3 = new SupportedTestNodeRule();
        final List<NodeRule> rules = Arrays.<NodeRule>asList(rule1, rule2, rule3);
        when(stylesheet.getRules()).thenReturn(rules);

        final List<NodeRule> rules1 = Arrays.<NodeRule>asList(rule1);
        final List<NodeRule> rules23 = Arrays.<NodeRule>asList(rule2, rule3);
        final List<NodeRule> rules2 = Arrays.<NodeRule>asList(rule2);
        final List<NodeRule> rules3 = Arrays.<NodeRule>asList(rule3);

        when(rulesSplitter.split(rules, limit)).thenReturn(new RulesContainer(rules1, rules23));
        when(rulesSplitter.split(rules23, limit)).thenReturn(new RulesContainer(rules2, rules3));
        when(rulesSplitter.split(rules3, limit)).thenReturn(new RulesContainer(rules3, Collections.<NodeRule>emptyList()));

        final List<StyleSheet> sheets = splliter.split(stylesheet);

        assertThat(sheets).hasSize(3);
        assertThat(sheets.get(0).getRules()).containsExactlyElementsOf(rules1);
        assertThat(sheets.get(1).getRules()).containsExactlyElementsOf(rules2);
        assertThat(sheets.get(2).getRules()).containsExactlyElementsOf(rules3);
    }
}
