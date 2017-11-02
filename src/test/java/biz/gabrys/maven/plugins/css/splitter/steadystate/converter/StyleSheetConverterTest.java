package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
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
        assertNotNull("Converted stylesheet instance should not be equal to null", convertedStyleSheet);
        final List<NodeRule> childRules = convertedStyleSheet.getRules();
        assertNotNull("Converted stylesheet rules instance should not be equal to null", childRules);
        assertEquals("Converted stylesheet rules size", 1, childRules.size());
        assertEquals("Converted stylesheet rule", convertedStyleRule, childRules.get(0));
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
        assertNotNull("Converter instance should not be equal to null", converter);

        final List<RuleConverter<?>> children = converter.converters;
        assertEquals("Converters quantity", 7, children.size());
        assertEquals("First converter class", StyleRuleConverter.class, children.get(0).getClass());
        assertEquals("Second converter class", MediaRuleConverter.class, children.get(1).getClass());
        assertEquals("Third converter class", FontFaceRuleConverter.class, children.get(2).getClass());
        assertEquals("Fourth converter class", PageRuleConverter.class, children.get(3).getClass());
        assertEquals("Fifth converter class", ImportRuleConverter.class, children.get(4).getClass());
        assertEquals("Sixth converter class", CharsetRuleConverter.class, children.get(5).getClass());
        assertEquals("Seventh converter class", UnknownRuleConverter.class, children.get(6).getClass());
    }
}
