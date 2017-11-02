package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.steadystate.css.dom.CSSPageRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class PageRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final PageRuleConverter converter = new PageRuleConverter();
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
    }

    @Test
    public void convert() {
        final PageRuleConverter converter = new PageRuleConverter();
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();
        rule.setCssText("@page :first { name: value; }");

        final StyleRule converted = converter.convert(rule);
        assertNotNull("Converted rule instance should not be equal to null", converted);
        final List<String> selectors = converted.getSelectors();
        assertNotNull("Converted rule selectors instance should not be equal to null", selectors);
        assertEquals("Converted rule selectors size", 1, selectors.size());
        assertEquals("Converted rule selector", "@page :first", selectors.get(0));
        final List<StyleProperty> properties = converted.getProperties();
        assertNotNull("Converted rule properties instance should not be equal to null", properties);
        assertEquals("Converted rule properties size", 1, properties.size());
        final StyleProperty styleProperty = properties.get(0);
        assertEquals("Converted rule property name", "name", styleProperty.getName());
        assertEquals("Converted rule property value", "value", styleProperty.getValue());
    }
}
