package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.steadystate.css.dom.CSSUnknownRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();

        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
    }

    @Test
    public void convert() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final String code = "@unknown";
        rule.setCssText(code);

        final UnknownRule converted = converter.convert(rule);

        assertNotNull(converted);
        assertEquals(code, converted.getCode());
    }
}
