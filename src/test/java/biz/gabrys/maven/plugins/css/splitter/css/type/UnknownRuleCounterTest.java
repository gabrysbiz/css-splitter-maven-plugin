package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class UnknownRuleCounterTest {

    @Test
    public void count_ruleDoesNotContainAnySemicolons_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@zero-semicolon");

        assertEquals(1, counter.count(rule));
    }

    @Test
    public void count_simpleRuleWhichContainsSemicolonAtEnd_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@rule 'value';");

        assertEquals(1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsZeroProperties_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown { }");

        assertEquals(1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value;}");

        assertEquals(1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value; val: value }");

        assertEquals(2, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value;\n}\t}");

        assertEquals(1, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value; prop2: val2 \n}\t}");

        assertEquals(2, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithAllSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value;}\n nested2 {prop1: val1;} nested3 { prop2: val2;\n}\t}");

        assertEquals(3, counter.count(rule));
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithMissingSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value}\n nested2 {prop1: val1} nested3 { prop2: val2\n}\t}");

        assertEquals(3, counter.count(rule));
    }
}
