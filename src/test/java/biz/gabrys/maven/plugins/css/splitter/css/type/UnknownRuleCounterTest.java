package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class UnknownRuleCounterTest {

    @Test
    public void count_ruleDoesNotContainAnySemicolons_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@zero-semicolon");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void count_simpleRuleWhichContainsSemicolonAtEnd_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@rule 'value';");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void count_complexRuleWhichContainsZeroProperties_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown { }");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void count_complexRuleWhichContainsOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value;}");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void count_complexRuleWhichContainsTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {val: value; val: value }");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(2);
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithOnePropertyAndOneSemicolon_returnsOne() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value;\n}\t}");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void count_complexRuleWhichContainsOneNestedPropertyWithTwoPropertiesAndOneSemicolon_returnsTwo() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule("@unknown {nested {property: value; prop2: val2 \n}\t}");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(2);
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithAllSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value;}\n nested2 {prop1: val1;} nested3 { prop2: val2;\n}\t}");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(3);
    }

    @Test
    public void count_complexRuleWhichContainsThreeNestedPropertiesWithMissingSemicolons_returnsThree() {
        final UnknownRuleCounter counter = new UnknownRuleCounter();
        final UnknownRule rule = new UnknownRule(
                "@unknown { nested1 {property: value}\n nested2 {prop1: val1} nested3 { prop2: val2\n}\t}");

        final int result = counter.count(rule);

        assertThat(result).isEqualTo(3);
    }
}
