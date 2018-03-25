package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.w3c.css.sac.Selector;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

@RunWith(MockitoJUnitRunner.class)
public class CssFormatterTest {

    private static final String CODE = "code";

    @Spy
    private CssFormatter formatter;

    @Test
    public void format_cssRuleWhichIsNotAnInstaceOfCSSFormatable_returnsCodeWithoutFormatting() {
        final CSSRule rule = mock(CSSRule.class);
        when(rule.getCssText()).thenReturn(CODE);

        final String result = formatter.format(rule);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(rule);
        verify(rule).getCssText();
        verifyNoMoreInteractions(formatter, rule);
    }

    @Test
    public void format_cssRuleWhichIsAnInstaceOfCSSFormatable_returnsCodeWithFormatting() {
        final CSSRule rule = mock(CSSRule.class, withSettings().extraInterfaces(CSSFormatable.class));
        doReturn(CODE).when(formatter).format(any(CSSFormatable.class));

        final String result = formatter.format(rule);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(rule);
        verify(formatter).format((CSSFormatable) rule);
        verifyNoMoreInteractions(formatter);
        verifyZeroInteractions(rule);
    }

    @Test
    public void format_selectorWhichIsNotAnInstaceOfCSSFormatable_returnsCodeWithoutFormatting() {
        final Selector selector = mock(Selector.class);
        when(selector.toString()).thenReturn(CODE);

        final String result = formatter.format(selector);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(selector);
        verifyNoMoreInteractions(formatter, selector);
    }

    @Test
    public void format_selectorWhichIsAnInstaceOfCSSFormatable_returnsCodeWithFormatting() {
        final Selector selector = mock(Selector.class, withSettings().extraInterfaces(CSSFormatable.class));
        doReturn(CODE).when(formatter).format(any(CSSFormatable.class));

        final String result = formatter.format(selector);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(selector);
        verify(formatter).format((CSSFormatable) selector);
        verifyNoMoreInteractions(formatter);
        verifyZeroInteractions(selector);
    }

    @Test
    public void format_cssValueWhichIsNotAnInstaceOfCSSFormatable_returnsCodeWithoutFormatting() {
        final CSSValue value = mock(CSSValue.class);
        when(value.getCssText()).thenReturn(CODE);

        final String result = formatter.format(value);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(value);
        verify(value).getCssText();
        verifyNoMoreInteractions(formatter, value);
    }

    @Test
    public void format_cssValueWhichIsAnInstaceOfCSSFormatable_returnsCodeWithFormatting() {
        final CSSValue value = mock(CSSValue.class, withSettings().extraInterfaces(CSSFormatable.class));
        doReturn(CODE).when(formatter).format(any(CSSFormatable.class));

        final String result = formatter.format(value);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(value);
        verify(formatter).format((CSSFormatable) value);
        verifyNoMoreInteractions(formatter);
        verifyZeroInteractions(value);
    }

    @Test
    public void format_cssFormatable_returnsCodeWithFormatting() {
        final CSSFormatable formatable = mock(CSSFormatable.class);
        when(formatable.getCssText(any(CSSFormat.class))).thenReturn(CODE);

        final String result = formatter.format(formatable);

        assertThat(result).isEqualTo(CODE);
        verify(formatter).format(formatable);
        final ArgumentCaptor<CSSFormat> captor = ArgumentCaptor.forClass(CSSFormat.class);
        verify(formatable).getCssText(captor.capture());
        final CSSFormat format = captor.getValue();
        assertThat(format).isNotNull();
        assertThat(format.isRgbAsHex()).isTrue();
        assertThat(format.useSourceStringValues()).isTrue();
        assertThat(format.getPropertiesInSeparateLines()).isFalse();
        assertThat(format.getPropertiesIndent()).isEmpty();
        verifyNoMoreInteractions(formatter, formatable);
    }
}
