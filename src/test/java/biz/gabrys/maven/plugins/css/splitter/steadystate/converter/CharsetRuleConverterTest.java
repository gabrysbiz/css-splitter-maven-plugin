package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.steadystate.css.dom.CSSCharsetRuleImpl;
import com.steadystate.css.format.CSSFormatable;

import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;

public final class CharsetRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final CssFormatter formatter = mock(CssFormatter.class);
        final CharsetRuleConverter converter = new CharsetRuleConverter(formatter);
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        final String code = "@charset 'utf-8'";
        when(formatter.format((CSSFormatable) rule)).thenReturn(code);

        final SimpleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo(code);
    }
}
