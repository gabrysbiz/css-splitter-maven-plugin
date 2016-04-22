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
    public void toString_ruleHasZeroRules() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<StyleRule> rules = Collections.emptyList();

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String code = rule.toString();

        Assert.assertEquals("CSS code", "@media sel1, sel2 {\n}\n", code);
    }

    @Test
    public void toString_ruleHasTwoProperties() {
        final List<String> selectors = Arrays.asList("selector");

        final List<StyleRule> rules = new ArrayList<StyleRule>();
        final StyleRule styleRule1 = Mockito.mock(StyleRule.class);
        Mockito.when(styleRule1.getLines()).thenReturn(Arrays.asList("rule {", "  prop: val;", "}"));
        rules.add(styleRule1);
        final StyleRule styleRule2 = Mockito.mock(StyleRule.class);
        Mockito.when(styleRule2.getLines()).thenReturn(Arrays.asList("rule2 {", "}"));
        rules.add(styleRule2);

        final ComplexRule rule = new ComplexRule("@media", selectors, rules);
        final String code = rule.toString();

        Assert.assertEquals("CSS code", "@media selector {\n  rule {\n    prop: val;\n  }\n  rule2 {\n  }\n}\n", code);
    }
}
