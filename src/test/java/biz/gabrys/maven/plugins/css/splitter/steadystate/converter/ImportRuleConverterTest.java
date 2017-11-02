package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.steadystate.css.dom.CSSImportRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;

public final class ImportRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
    }

    @Test
    public void convert() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();
        final String code = "@import 'file.css';";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);
        assertNotNull("Converted rule instance should not be equal to null", converted);
        assertEquals("Converted rule code", "@import url(file.css);", converted.getCode());
    }
}
