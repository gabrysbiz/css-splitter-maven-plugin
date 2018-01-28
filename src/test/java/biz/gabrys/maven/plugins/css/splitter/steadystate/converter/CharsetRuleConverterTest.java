package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.steadystate.css.dom.CSSCharsetRuleImpl;

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
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        final String code = "@charset \"UTF-8\";";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo(code);
    }
}
