package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class FontFaceRuleConverterTest {

    @Test
    public void getSupportedType_returnsCSSFontFaceRuleImplClass() {
        final FontFaceRuleConverter converter = new FontFaceRuleConverter();
        Assert.assertEquals("Supported type", CSSFontFaceRuleImpl.class, converter.getSupportedType());
    }

    @Test
    public void convert() {
        final FontFaceRuleConverter converter = new FontFaceRuleConverter();
        final CSSFontFaceRuleImpl rule = new CSSFontFaceRuleImpl();
        final CSSStyleDeclarationImpl style = new CSSStyleDeclarationImpl(rule);
        final Property property = new Property();
        property.setName("name");
        final CSSValueImpl value = new CSSValueImpl();
        value.setCssText("value");
        property.setValue(value);
        style.addProperty(property);
        rule.setStyle(style);

        final StyleRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance", converted);
        final List<String> selectors = converted.getSelectors();
        Assert.assertNotNull("Converted rule selectors instance", selectors);
        Assert.assertEquals("Converted rule selectors size", 1, selectors.size());
        Assert.assertEquals("Converted rule selector", "@font-face", selectors.get(0));
        final List<StyleProperty> properties = converted.getProperties();
        Assert.assertNotNull("Converted rule properties instance", properties);
        Assert.assertEquals("Converted rule properties size", 1, properties.size());
        final StyleProperty styleProperty = properties.get(0);
        Assert.assertEquals("Converted rule property name", property.getName(), styleProperty.getName());
        Assert.assertEquals("Converted rule property value", value.getCssText(), styleProperty.getValue());
    }
}
