package biz.gabrys.maven.plugins.css.splitter.counter;

import org.junit.Assert;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class UnknownRuleCounterTest {

    @Test
    public void count_ruleDoesNotContainAnySemicolons_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@zero-semicolon");

        Assert.assertEquals("Counted value", 1, counter.count(rule));
    }

    @Test
    public void count_simpleRuleWhichContainsSemicolonAtEnd_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@rule 'value';");

        Assert.assertEquals("Counted value", 1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value;}");

        Assert.assertEquals("Counted value", 1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsTowPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value; val: value}");

        Assert.assertEquals("Counted value", 2, counter.count(rule));
    }
}
