package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.steadystate.css.dom.CSSUnknownRuleImpl;

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
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final String code = "@unknown";
        rule.setCssText(code);

        final UnknownRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo(code);
    }
}
