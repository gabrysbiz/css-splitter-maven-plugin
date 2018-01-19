package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.steadystate.css.dom.CSSPageRuleImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class PageRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final PageRuleConverter converter = new PageRuleConverter();
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final StylePropertyConverter stylePropertyConverter = mock(StylePropertyConverter.class);
        final PageRuleConverter converter = new PageRuleConverter(stylePropertyConverter);
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();
        rule.setCssText("@page :first { name1: value1; name2: value2; }");

        final StyleProperty styleProperty1 = mock(StyleProperty.class);
        final StyleProperty styleProperty2 = mock(StyleProperty.class);
        when(stylePropertyConverter.convert(any(Property.class))).thenReturn(styleProperty1, styleProperty2);

        final StyleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly("@page :first");
        final List<StyleProperty> properties = converted.getProperties();
        assertThat(properties).hasSize(2);
        assertThat(properties.get(0)).isSameAs(styleProperty1);
        assertThat(properties.get(1)).isSameAs(styleProperty2);
    }
}
