package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.steadystate.css.dom.CSSImportRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;

public final class ImportRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
    }

    @Test
    public void convert() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();
        final String code = "@import 'file.css';";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo("@import url(file.css);");
    }
}
