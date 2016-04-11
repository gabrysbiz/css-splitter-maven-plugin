package biz.gabrys.maven.plugins.css.splitter.counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class StyleRuleCounterTest {

    @Test
    public void count_emptyRule_returnsOne() {
        final StyleRuleCounter counter = new StyleRuleCounter();
        final StyleRule rule = new StyleRule(Collections.<String>emptyList(), Collections.<StyleProperty>emptyList());

        Assert.assertEquals("Properties count", 1, counter.count(rule));
    }

    @Test
    public void count_ruleWithThreeProperties_returnsThree() {
        final StyleRuleCounter counter = new StyleRuleCounter();

        final List<StyleProperty> properties = new ArrayList<StyleProperty>();
        properties.add(Mockito.mock(StyleProperty.class));
        properties.add(Mockito.mock(StyleProperty.class));
        properties.add(Mockito.mock(StyleProperty.class));

        final StyleRule rule = new StyleRule(Collections.<String>emptyList(), properties);

        Assert.assertEquals("Properties count", 3, counter.count(rule));
    }
}
