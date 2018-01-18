package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.w3c.dom.css.CSSRule;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class MultipleRuleConverterTest {

    @Test
    public void isSupportedType_convertersNotSupportRule_returnsFalse() {
        final CSSRule rule = mock(CSSRule.class);
        final RuleConverter<?> internalConverter1 = mock(RuleConverter.class);
        when(internalConverter1.isSupportedType(rule)).thenReturn(false);
        final RuleConverter<?> internalConverter2 = mock(RuleConverter.class);
        when(internalConverter2.isSupportedType(rule)).thenReturn(false);
        final RuleConverter<NodeRule> converter = new MultipleRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isFalse();
        verify(internalConverter1).isSupportedType(rule);
        verify(internalConverter2).isSupportedType(rule);
        verifyZeroInteractions(internalConverter1, internalConverter2);
    }

    @Test
    public void isSupported_atLeastOneConverterSupportsRule_returnsTrue() {
        final CSSRule rule = mock(CSSRule.class);
        final RuleConverter<?> internalConverter1 = mock(RuleConverter.class);
        when(internalConverter1.isSupportedType(rule)).thenReturn(false);
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = mock(RuleConverter.class);
        when(internalConverter2.isSupportedType(rule)).thenReturn(true);
        final RuleConverter<NodeRule> converter = new MultipleRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
        verify(internalConverter1).isSupportedType(rule);
        verify(internalConverter2).isSupportedType(rule);
        verifyNoMoreInteractions(internalConverter1, internalConverter2);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_convertersNotSupportRule_throwsException() {
        final CSSRule rule = mock(CSSRule.class);
        final RuleConverter<?> internalConverter1 = mock(RuleConverter.class);
        when(internalConverter1.isSupportedType(rule)).thenReturn(false);
        final RuleConverter<?> internalConverter2 = mock(RuleConverter.class);
        when(internalConverter2.isSupportedType(rule)).thenReturn(false);
        final RuleConverter<NodeRule> converter = new MultipleRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        converter.convert(rule);
    }

    @Test
    public void convert_atLeastOneConverterSupportsRule_returnsRule() {
        final CSSRule rule = mock(CSSRule.class);
        final RuleConverter<?> internalConverter1 = mock(RuleConverter.class);
        when(internalConverter1.isSupportedType(rule)).thenReturn(false);
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = mock(RuleConverter.class);
        when(internalConverter2.isSupportedType(rule)).thenReturn(true);
        final StyleRule styleRule = mock(StyleRule.class);
        when(internalConverter2.convert(rule)).thenReturn(styleRule);
        final RuleConverter<NodeRule> converter = new MultipleRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final NodeRule converted = converter.convert(rule);

        assertThat(converted).isEqualTo(styleRule);
        verify(internalConverter1).isSupportedType(rule);
        verify(internalConverter2).isSupportedType(rule);
        verify(internalConverter2).convert(rule);
        verifyNoMoreInteractions(internalConverter1, internalConverter2);
    }

    @Test
    public void convert_twoConvertersSupportTheSameType_returnsRuleConvertedByFirstConverter() {
        final CSSRule rule = mock(CSSRule.class);
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter1 = mock(RuleConverter.class);
        when(internalConverter1.isSupportedType(rule)).thenReturn(true);
        final StyleRule styleRule = mock(StyleRule.class);
        when(internalConverter1.convert(rule)).thenReturn(styleRule);
        @SuppressWarnings("unchecked")
        final RuleConverter<StyleRule> internalConverter2 = mock(RuleConverter.class);
        final RuleConverter<NodeRule> converter = new MultipleRuleConverter(
                Arrays.<RuleConverter<?>>asList(internalConverter1, internalConverter2));

        final NodeRule converted = converter.convert(rule);

        assertThat(converted).isEqualTo(styleRule);
        verify(internalConverter1).isSupportedType(rule);
        verify(internalConverter1).convert(rule);
        verifyNoMoreInteractions(internalConverter1);
        verifyZeroInteractions(internalConverter2);
    }
}
