package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
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
        final StylePropertyConverter stylePropertyConverter = mock(StylePropertyConverter.class);
        final FontFaceRuleConverter converter = new FontFaceRuleConverter(stylePropertyConverter);

        final CSSFontFaceRuleImpl rule = new CSSFontFaceRuleImpl();
        final CSSStyleDeclarationImpl style = new CSSStyleDeclarationImpl(rule);
        final Property property1 = mock(Property.class);
        style.addProperty(property1);
        final Property property2 = mock(Property.class);
        style.addProperty(property2);
        rule.setStyle(style);

        final StyleProperty styleProperty1 = mock(StyleProperty.class);
        when(stylePropertyConverter.convert(property1)).thenReturn(styleProperty1);
        final StyleProperty styleProperty2 = mock(StyleProperty.class);
        when(stylePropertyConverter.convert(property2)).thenReturn(styleProperty2);

        final StyleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly("@font-face");
        final List<StyleProperty> properties = converted.getProperties();
        assertThat(properties).hasSize(2);
        assertThat(properties.get(0)).isSameAs(styleProperty1);
        assertThat(properties.get(1)).isSameAs(styleProperty2);
    }
}
