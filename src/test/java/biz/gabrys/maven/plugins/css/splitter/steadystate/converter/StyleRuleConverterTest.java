package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final StyleRuleConverter converter = new StyleRuleConverter();
        final CSSStyleRuleImpl rule = new CSSStyleRuleImpl();

        final SelectorListImpl selectorList = new SelectorListImpl();
        final String selector = "selector";
        selectorList.add(new Selector() {

            @Override
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

        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly(selector);
        final List<StyleProperty> properties = converted.getProperties();
        assertThat(properties).hasSize(1);
        final StyleProperty styleProperty = properties.get(0);
        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo(property.getName());
        assertThat(styleProperty.getValue()).isEqualTo(value.getCssText());
    }
}
