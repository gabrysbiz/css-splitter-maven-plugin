package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.steadystate.css.dom.CSSCharsetRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;

public final class CharsetRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
    }

    @Test
    public void convert() {
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        final String code = "@charset \"UTF-8\";";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);

        assertNotNull("Converted rule instance should not be equal to null", converted);
        assertEquals("Converted rule code", code, converted.getCode());
    }
}
