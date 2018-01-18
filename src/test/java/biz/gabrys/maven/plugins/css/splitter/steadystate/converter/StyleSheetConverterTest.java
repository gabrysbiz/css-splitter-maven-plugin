package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StyleSheetConverterTest {

    @Test
    public void convert_containSupportedRules_returnsStyleSheet() {
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter = mock(RuleConverter.class);
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        when(internalConverter.isSupportedType(styleRule)).thenReturn(true);
        final StyleRule convertedStyleRule = mock(StyleRule.class);
        when(internalConverter.convert(styleRule)).thenReturn(convertedStyleRule);

        final StyleSheetConverter converter = new StyleSheetConverter(internalConverter);
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(styleRule);
        stylesheet.setCssRules(ruleList);

        final StyleSheet convertedStyleSheet = converter.convert(stylesheet);

        assertThat(convertedStyleSheet).isNotNull();
        assertThat(convertedStyleSheet.getRules()).containsExactly(convertedStyleRule);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_containUnsupportedRules_throwsException() {
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter = mock(RuleConverter.class);
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        when(internalConverter.isSupportedType(styleRule)).thenReturn(false);

        final StyleSheetConverter converter = new StyleSheetConverter(internalConverter);
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(styleRule);
        stylesheet.setCssRules(ruleList);
        converter.convert(stylesheet);
    }

    @Test
    public void createConverter_returnsMultipleRuleConverter() {
        final MultipleRuleConverter converter = StyleSheetConverter.createConverter(Standard.VERSION_3_0, true);

        assertThat(converter).isNotNull();
        assertThat(converter.converters).hasSize(7);
        assertThat(converter.converters.get(0)).isExactlyInstanceOf(StyleRuleConverter.class);
        assertThat(converter.converters.get(1)).isExactlyInstanceOf(MediaRuleConverter.class);
        assertThat(converter.converters.get(2)).isExactlyInstanceOf(FontFaceRuleConverter.class);
        assertThat(converter.converters.get(3)).isExactlyInstanceOf(PageRuleConverter.class);
        assertThat(converter.converters.get(4)).isExactlyInstanceOf(ImportRuleConverter.class);
        assertThat(converter.converters.get(5)).isExactlyInstanceOf(CharsetRuleConverter.class);
        assertThat(converter.converters.get(6)).isExactlyInstanceOf(UnknownRuleConverter.class);
    }
}
