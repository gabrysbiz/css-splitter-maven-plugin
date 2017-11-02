package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.w3c.css.sac.Selector;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.parser.SelectorListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class StyleRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final StyleRuleConverter converter = new StyleRuleConverter();
        final CSSStyleRuleImpl rule = new CSSStyleRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
    }

    @Test
    public void convert() {
        final StyleRuleConverter converter = new StyleRuleConverter();
        final CSSStyleRuleImpl rule = new CSSStyleRuleImpl();

        final SelectorListImpl selectorList = new SelectorListImpl();
        final String selector = "selector";
        selectorList.add(new Selector() {

            public short getSelectorType() {
                return 0;
            }

            @Override
            public String toString() {
                return selector;
            }
        });
        rule.setSelectors(selectorList);

        final CSSStyleDeclarationImpl style = new CSSStyleDeclarationImpl(rule);
        final Property property = new Property();
        property.setName("name");
        final CSSValueImpl value = new CSSValueImpl();
        value.setCssText("value");
        property.setValue(value);
        style.addProperty(property);
        rule.setStyle(style);

        final StyleRule converted = converter.convert(rule);
        assertNotNull("Converted rule instance should not be equal to null", converted);
        final List<String> selectors = converted.getSelectors();
        assertNotNull("Converted rule selectors instance should not be equal to null", selectors);
        assertEquals("Converted rule selectors size", 1, selectors.size());
        assertEquals("Converted rule selector", selector, selectors.get(0));
        final List<StyleProperty> properties = converted.getProperties();
        assertNotNull("Converted rule properties instance should not be equal to null", properties);
        assertEquals("Converted rule properties size", 1, properties.size());
        final StyleProperty styleProperty = properties.get(0);
        assertEquals("Converted rule property name", property.getName(), styleProperty.getName());
        assertEquals("Converted rule property value", value.getCssText(), styleProperty.getValue());
    }
}
