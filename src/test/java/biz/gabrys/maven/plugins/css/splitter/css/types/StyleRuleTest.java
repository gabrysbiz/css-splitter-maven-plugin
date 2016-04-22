package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public final class StyleRuleTest {

    @Test
    public void toString_ruleHasZeroProperties() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final String code = rule.toString();

        Assert.assertEquals("CSS code", "sel1, sel2 {\n}\n", code);
    }

    @Test
    public void toString_ruleHasTwoProperties() {
        final List<String> selectors = Arrays.asList("div", "#id", "ul > li");
        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(new StyleProperty("name", "value"));
        properties.add(new StyleProperty("prop", "val"));

        final StyleRule rule = new StyleRule(selectors, properties);
        final String code = rule.toString();

        Assert.assertEquals("CSS code", "div, #id, ul > li {\n  name: value;\n  prop: val;\n}\n", code);
    }
}
