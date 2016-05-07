package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRuleImpl;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public class SplliterTest {

    @Test
    public void split_styleSheetContainsZeroRules_returnOneEmptyStyleSheet() {
        @SuppressWarnings("unchecked")
        final RulesSplitter<NodeRule> rulesSplitter = Mockito.mock(RulesSplitter.class);
        final Splliter splliter = new Splliter(0, rulesSplitter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        final List<StyleSheet> sheets = splliter.split(stylesheet);
        Assert.assertEquals("Sheets quantity", 1, sheets.size());
        Assert.assertEquals("First document", stylesheet, sheets.get(0));
    }

    @Test
    public void split_styleSheetWillBeSplitedToThreeDocuments_returnsThreeDocuments() {
        final int limit = 1;
        @SuppressWarnings("unchecked")
        final RulesSplitter<NodeRule> rulesSplitter = Mockito.mock(RulesSplitter.class);
        final Splliter splliter = new Splliter(limit, rulesSplitter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule1 = new NodeRuleImpl();
        final NodeRule rule2 = new NodeRuleImpl();
        final NodeRule rule3 = new NodeRuleImpl();
        final List<NodeRule> rules = Arrays.<NodeRule>asList(rule1, rule2, rule3);
        Mockito.when(stylesheet.getRules()).thenReturn(rules);

        final List<NodeRule> rules1 = Arrays.<NodeRule>asList(rule1);
        final List<NodeRule> rules23 = Arrays.<NodeRule>asList(rule2, rule3);
        final List<NodeRule> rules2 = Arrays.<NodeRule>asList(rule2);
        final List<NodeRule> rules3 = Arrays.<NodeRule>asList(rule3);

        Mockito.when(rulesSplitter.split(rules, limit)).thenReturn(new RulesContainer<NodeRule>(rules1, rules23));
        Mockito.when(rulesSplitter.split(rules23, limit)).thenReturn(new RulesContainer<NodeRule>(rules2, rules3));
        Mockito.when(rulesSplitter.split(rules3, limit))
                .thenReturn(new RulesContainer<NodeRule>(rules3, Collections.<NodeRule>emptyList()));

        final List<StyleSheet> sheets = splliter.split(stylesheet);
        Assert.assertEquals("Sheets quantity", 3, sheets.size());
        Assert.assertEquals("First document rules", rules1, sheets.get(0).getRules());
        Assert.assertEquals("Second document rules", rules2, sheets.get(1).getRules());
        Assert.assertEquals("Third document rules", rules3, sheets.get(2).getRules());
    }
}
