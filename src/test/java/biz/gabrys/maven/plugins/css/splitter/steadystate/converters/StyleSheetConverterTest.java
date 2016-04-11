package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class StyleSheetConverterTest {

    @Test
    public void convert_containSupportedRules_returnsStyleSheet() {
        final List<RuleConverter<?, ?>> converters = new ArrayList<RuleConverter<?, ?>>();
        @SuppressWarnings("unchecked")
        final RuleConverter<CSSStyleRuleImpl, StyleRule> styleConverter = Mockito.mock(RuleConverter.class);
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        Mockito.when(styleConverter.getSupportedType()).thenReturn(CSSStyleRuleImpl.class);
        final StyleRule convertedStyleRule = Mockito.mock(StyleRule.class);
        Mockito.when(styleConverter.convert(styleRule)).thenReturn(convertedStyleRule);
        converters.add(styleConverter);

        final StyleSheetConverter converter = new StyleSheetConverter(converters);
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(styleRule);
        stylesheet.setCssRules(ruleList);

        final StyleSheet convertedStyleSheet = converter.convert(stylesheet);
        Assert.assertNotNull("Converted stylesheet instance", convertedStyleSheet);
        final List<NodeRule> childRules = convertedStyleSheet.getRules();
        Assert.assertNotNull("Converted stylesheet rules instance", childRules);
        Assert.assertEquals("Converted stylesheet rules size", 1, childRules.size());
        Assert.assertEquals("Converted stylesheet rule", convertedStyleRule, childRules.get(0));
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_containUnsupportedRules_throwsException() {
        final StyleSheetConverter converter = new StyleSheetConverter(Collections.<RuleConverter<?, ?>>emptyList());
        final CSSStyleSheetImpl stylesheet = new CSSStyleSheetImpl();
        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        ruleList.add(new CSSStyleRuleImpl());
        stylesheet.setCssRules(ruleList);
        converter.convert(stylesheet);
    }
}
