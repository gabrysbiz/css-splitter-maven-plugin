package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StyleSheetConverterTest {

    @Test
    public void convert_containSupportedRules_returnsStyleSheet() {
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter = Mockito.mock(RuleConverter.class);
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        Mockito.when(internalConverter.isSupportedType(styleRule)).thenReturn(true);
        final StyleRule convertedStyleRule = Mockito.mock(StyleRule.class);
        Mockito.when(internalConverter.convert(styleRule)).thenReturn(convertedStyleRule);

        final StyleSheetConverter converter = new StyleSheetConverter(internalConverter);
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(styleRule);
        stylesheet.setCssRules(ruleList);

        final StyleSheet convertedStyleSheet = converter.convert(stylesheet);
        Assert.assertNotNull("Converted stylesheet instance.", convertedStyleSheet);
        final List<NodeRule> childRules = convertedStyleSheet.getRules();
        Assert.assertNotNull("Converted stylesheet rules instance.", childRules);
        Assert.assertEquals("Converted stylesheet rules size.", 1, childRules.size());
        Assert.assertEquals("Converted stylesheet rule.", convertedStyleRule, childRules.get(0));
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_containUnsupportedRules_throwsException() {
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter = Mockito.mock(RuleConverter.class);
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        Mockito.when(internalConverter.isSupportedType(styleRule)).thenReturn(false);

        final StyleSheetConverter converter = new StyleSheetConverter(internalConverter);
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(styleRule);
        stylesheet.setCssRules(ruleList);
        converter.convert(stylesheet);
    }
}
