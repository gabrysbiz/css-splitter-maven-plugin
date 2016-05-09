package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class StyleRuleTest {

    @Test
    public void getLines_ruleHasZeroProperties() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 2, lines.length);
        Assert.assertEquals("Line no. 1.", "sel1, sel2 {", lines[0]);
        Assert.assertEquals("Line no. 2.", "}", lines[1]);
    }

    @Test
    public void getLines_ruleHasTwoProperties() {
        final List<String> selectors = Arrays.asList("div", "#id", "ul > li");
        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(new StyleProperty("name", "value"));
        properties.add(new StyleProperty("prop", "val"));

        final StyleRule rule = new StyleRule(selectors, properties);
        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 4, lines.length);
        Assert.assertEquals("Line no. 1.", "div, #id, ul > li {", lines[0]);
        Assert.assertEquals("Line no. 2.", "  name: value;", lines[1]);
        Assert.assertEquals("Line no. 3.", "  prop: val;", lines[2]);
        Assert.assertEquals("Line no. 4.", "}", lines[3]);
    }

    @Test
    public void getSize2_emptyRule_returnsOne() {
        final List<String> selectors = Collections.emptyList();
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final int size = rule.getSize2();

        Assert.assertEquals("Rule size.", 1, size);
    }

    @Test
    public void getSize2_ruleWithThreeProperties_returnsThree() {
        final List<String> selectors = Collections.emptyList();

        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(Mockito.mock(StyleProperty.class));
        properties.add(Mockito.mock(StyleProperty.class));
        properties.add(Mockito.mock(StyleProperty.class));

        final StyleRule rule = new StyleRule(selectors, properties);
        final int size = rule.getSize2();

        Assert.assertEquals("Rule size.", 3, size);
    }
}
