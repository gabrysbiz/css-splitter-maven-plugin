package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public final class StyleRuleTest {

    @Test
    public void getLines_ruleHasZeroProperties() {
        final List<String> selectors = Arrays.asList("sel1", "sel2");
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final String[] lines = rule.getLines();

        assertNotNull("Lines object should not be equal to null", lines);
        assertEquals("Lines quantity", 2, lines.length);
        assertEquals("Line no. 1", "sel1, sel2 {", lines[0]);
        assertEquals("Line no. 2", "}", lines[1]);
    }

    @Test
    public void getLines_ruleHasTwoProperties() {
        final List<String> selectors = Arrays.asList("div", "#id", "ul > li");
        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(new StyleProperty("name", "value"));
        properties.add(new StyleProperty("prop", "val"));

        final StyleRule rule = new StyleRule(selectors, properties);
        final String[] lines = rule.getLines();

        assertNotNull("Lines object should not be equal to null", lines);
        assertEquals("Lines quantity", 4, lines.length);
        assertEquals("Line no. 1", "div, #id, ul > li {", lines[0]);
        assertEquals("Line no. 2", "  name: value;", lines[1]);
        assertEquals("Line no. 3", "  prop: val;", lines[2]);
        assertEquals("Line no. 4", "}", lines[3]);
    }

    @Test
    public void getSize2_emptyRule_returnsOne() {
        final List<String> selectors = Collections.emptyList();
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final int size = rule.getSize2();

        assertEquals(1, size);
    }

    @Test
    public void getSize2_ruleWithThreeProperties_returnsThree() {
        final List<String> selectors = Collections.emptyList();

        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(mock(StyleProperty.class));
        properties.add(mock(StyleProperty.class));
        properties.add(mock(StyleProperty.class));

        final StyleRule rule = new StyleRule(selectors, properties);
        final int size = rule.getSize2();

        assertEquals(3, size);
    }
}
