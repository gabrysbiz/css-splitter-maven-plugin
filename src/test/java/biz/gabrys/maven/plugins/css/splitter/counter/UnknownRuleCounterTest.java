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
    public void count_complexRuleWhichContainsTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value; val: value }");

        Assert.assertEquals("Counted value", 2, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value;\n}\t}");

        Assert.assertEquals("Counted value", 1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value; prop2: val2 \n}\t}");

        Assert.assertEquals("Counted value", 2, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithAllSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value;}\n nested2 {prop1: val1;} nested3 { prop2: val2;\n}\t}");

        Assert.assertEquals("Counted value", 3, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithMissingSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value}\n nested2 {prop1: val1} nested3 { prop2: val2\n}\t}");

        Assert.assertEquals("Counted value", 3, counter.count(rule));
    }
}
