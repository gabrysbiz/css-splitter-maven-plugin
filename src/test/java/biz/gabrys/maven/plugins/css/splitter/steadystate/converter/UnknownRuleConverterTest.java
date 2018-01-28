package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.steadystate.css.dom.CSSUnknownRuleImpl;
import com.steadystate.css.format.CSSFormatable;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final CssFormatter formatter = mock(CssFormatter.class);
        final UnknownRuleConverter converter = new UnknownRuleConverter(formatter);
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final String code = "@unknown";
        when(formatter.format((CSSFormatable) rule)).thenReturn(code);

        final UnknownRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo(code);
    }
}
