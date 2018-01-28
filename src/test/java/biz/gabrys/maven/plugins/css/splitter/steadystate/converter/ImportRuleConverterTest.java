package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.steadystate.css.dom.CSSImportRuleImpl;
import com.steadystate.css.format.CSSFormatable;

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
        final CssFormatter formatter = mock(CssFormatter.class);
        final ImportRuleConverter converter = new ImportRuleConverter(formatter);
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();
        final String code = "@import";
        when(formatter.format((CSSFormatable) rule)).thenReturn(code);

        final SimpleRule converted = converter.convert(rule);

        assertThat(converted).isNotNull();
        assertThat(converted.getCode()).isEqualTo(code);
    }
}
