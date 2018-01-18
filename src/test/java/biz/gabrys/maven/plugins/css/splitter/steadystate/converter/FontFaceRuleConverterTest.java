package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class FontFaceRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final FontFaceRuleConverter converter = new FontFaceRuleConverter();
        final CSSFontFaceRuleImpl rule = new CSSFontFaceRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
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
        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly("@font-face");
        final List<StyleProperty> properties = converted.getProperties();
        assertThat(properties).hasSize(1);
        final StyleProperty styleProperty = properties.get(0);
        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo(property.getName());
        assertThat(styleProperty.getValue()).isEqualTo(value.getCssText());
    }
}
