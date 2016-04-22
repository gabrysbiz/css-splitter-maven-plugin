package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.css.CSSRule;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class AnyRuleConverterTest {

    @Test
    public void isSupportedType_convertersNotSupportRule_returnsFalse() {
        final CSSRule rule = Mockito.mock(CSSRule.class);

        final RuleConverter<?> internalConverter1 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter1.isSupportedType(rule)).thenReturn(false);

        final RuleConverter<?> internalConverter2 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter2.isSupportedType(rule)).thenReturn(false);

        final RuleConverter<NodeRule> converter = new AnyRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final boolean supported = converter.isSupportedType(rule);

        Assert.assertFalse("Should not support rule", supported);
    }

    @Test
    public void isSupported_atLeastOneConverterSupportsRule_returnsTrue() {
        final CSSRule rule = Mockito.mock(CSSRule.class);

        final RuleConverter<?> internalConverter1 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter1.isSupportedType(rule)).thenReturn(false);

        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter2.isSupportedType(rule)).thenReturn(true);

        final RuleConverter<NodeRule> converter = new AnyRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final boolean supported = converter.isSupportedType(rule);

        Assert.assertTrue("Should not support rule", supported);
        Mockito.verify(internalConverter1).isSupportedType(rule);
        Mockito.verify(internalConverter2).isSupportedType(rule);
        Mockito.verifyNoMoreInteractions(internalConverter1, internalConverter2);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_convertersNotSupportRule_throwsException() {
        final CSSRule rule = Mockito.mock(CSSRule.class);

        final RuleConverter<?> internalConverter1 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter1.isSupportedType(rule)).thenReturn(false);

        final RuleConverter<?> internalConverter2 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter2.isSupportedType(rule)).thenReturn(false);

        final RuleConverter<NodeRule> converter = new AnyRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        converter.convert(rule);
    }

    @Test
    public void convert_atLeastOneConverterSupportsRule_returnsRule() {
        final CSSRule rule = Mockito.mock(CSSRule.class);

        final RuleConverter<?> internalConverter1 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter1.isSupportedType(rule)).thenReturn(false);

        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter2.isSupportedType(rule)).thenReturn(true);
        final StyleRule styleRule = Mockito.mock(StyleRule.class);
        Mockito.when(internalConverter2.convert(rule)).thenReturn(styleRule);

        final RuleConverter<NodeRule> converter = new AnyRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final NodeRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule should not be null", converted);
        Assert.assertEquals("Converted rule", styleRule, converted);
        Mockito.verify(internalConverter1).isSupportedType(rule);
        Mockito.verify(internalConverter2).isSupportedType(rule);
        Mockito.verify(internalConverter2).convert(rule);
        Mockito.verifyNoMoreInteractions(internalConverter1, internalConverter2);
    }

    @Test
    public void convert_twoConvertersSupportTheSameType_returnsRuleConvertedByFirstConverter() {
        final CSSRule rule = Mockito.mock(CSSRule.class);

        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter1 = Mockito.mock(RuleConverter.class);
        Mockito.when(internalConverter1.isSupportedType(rule)).thenReturn(true);
        final StyleRule styleRule = Mockito.mock(StyleRule.class);
        Mockito.when(internalConverter1.convert(rule)).thenReturn(styleRule);

        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = Mockito.mock(RuleConverter.class);

        final RuleConverter<NodeRule> converter = new AnyRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final NodeRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule should not be null", converted);
        Assert.assertEquals("Converted rule", styleRule, converted);
        Mockito.verify(internalConverter1).isSupportedType(rule);
        Mockito.verify(internalConverter1).convert(rule);
        Mockito.verifyNoMoreInteractions(internalConverter1);
        Mockito.verifyZeroInteractions(internalConverter2);
    }
}
