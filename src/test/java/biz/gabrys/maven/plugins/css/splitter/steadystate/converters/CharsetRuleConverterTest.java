package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import org.junit.Assert;
import org.junit.Test;

import com.steadystate.css.dom.CSSCharsetRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;

public final class CharsetRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        final boolean supported = converter.isSupportedType(rule);
        Assert.assertTrue("Should return true.", supported);
    }

    @Test
    public void convert() {
        final CharsetRuleConverter converter = new CharsetRuleConverter();
        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        final String code = "@charset \"UTF-8\";";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance.", converted);
        Assert.assertEquals("Converted rule code.", code, converted.getCode());
    }
}
