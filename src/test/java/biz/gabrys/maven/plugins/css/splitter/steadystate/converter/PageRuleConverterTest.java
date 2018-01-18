package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final PageRuleConverter converter = new PageRuleConverter();
        final CSSPageRuleImpl rule = new CSSPageRuleImpl();
        rule.setCssText("@page :first { name: value; }");

        final StyleRule converted = converter.convert(rule);
        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly("@page :first");
        final List<StyleProperty> properties = converted.getProperties();
        assertThat(properties).hasSize(1);
        final StyleProperty styleProperty = properties.get(0);
        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo("name");
        assertThat(styleProperty.getValue()).isEqualTo("value");
    }
}
