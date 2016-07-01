package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import java.util.List;

import org.junit.Assert;
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
        Assert.assertTrue("Should return true.", supported);
    }

    @Test
    public void convert() {
        final PageRuleConverter converter = new PageRuleConverter();
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();
        rule.setCssText("@page :first { name: value; }");

        final StyleRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance.", converted);
        final List<String> selectors = converted.getSelectors();
        Assert.assertNotNull("Converted rule selectors instance.", selectors);
        Assert.assertEquals("Converted rule selectors size.", 1, selectors.size());
        Assert.assertEquals("Converted rule selector.", "@page :first", selectors.get(0));
        final List<StyleProperty> properties = converted.getProperties();
        Assert.assertNotNull("Converted rule properties instance.", properties);
        Assert.assertEquals("Converted rule properties size.", 1, properties.size());
        final StyleProperty styleProperty = properties.get(0);
        Assert.assertEquals("Converted rule property name.", "name", styleProperty.getName());
        Assert.assertEquals("Converted rule property value.", "value", styleProperty.getValue());
    }
}
