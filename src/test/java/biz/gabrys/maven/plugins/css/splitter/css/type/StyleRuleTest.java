package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;
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

        assertThat(lines).hasSize(2);
        assertThat(lines[0]).isEqualTo("sel1, sel2 {");
        assertThat(lines[1]).isEqualTo("}");
    }

    @Test
    public void getLines_ruleHasTwoProperties() {
        final List<String> selectors = Arrays.asList("div", "#id", "ul > li");
        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(new StyleProperty("name", "value"));
        properties.add(new StyleProperty("prop", "val"));

        final StyleRule rule = new StyleRule(selectors, properties);
        final String[] lines = rule.getLines();

        assertThat(lines).hasSize(4);
        assertThat(lines[0]).isEqualTo("div, #id, ul > li {");
        assertThat(lines[1]).isEqualTo("  name: value;");
        assertThat(lines[2]).isEqualTo("  prop: val;");
        assertThat(lines[3]).isEqualTo("}");
    }

    @Test
    public void getSize2_emptyRule_returnsOne() {
        final List<String> selectors = Collections.emptyList();
        final List<StyleProperty> properties = Collections.emptyList();

        final StyleRule rule = new StyleRule(selectors, properties);
        final int size = rule.getSize2();

        assertThat(size).isEqualTo(1);
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

        assertThat(size).isEqualTo(3);
    }
}
