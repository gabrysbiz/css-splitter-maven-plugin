package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import org.junit.Assert;
import org.junit.Test;

import com.steadystate.css.dom.CSSUnknownRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final boolean supported = converter.isSupportedType(rule);
        Assert.assertTrue("Should return true.", supported);
    }

    @Test
    public void convert() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final String code = "@unknown";
        rule.setCssText(code);

        final UnknownRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance.", converted);
        Assert.assertEquals("Converted rule code.", code, converted.getCode());
    }
}
