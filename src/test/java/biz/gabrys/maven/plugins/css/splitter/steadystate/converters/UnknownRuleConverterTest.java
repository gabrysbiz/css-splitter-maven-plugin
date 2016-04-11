package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import org.junit.Assert;
import org.junit.Test;

import com.steadystate.css.dom.CSSUnknownRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class UnknownRuleConverterTest {

    @Test
    public void getSupportedType_returnsCSSUnknownRuleImplClass() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        Assert.assertEquals("Supported type", CSSUnknownRuleImpl.class, converter.getSupportedType());
    }

    @Test
    public void convert() {
        final UnknownRuleConverter converter = new UnknownRuleConverter();
        final CSSUnknownRuleImpl rule = new CSSUnknownRuleImpl();
        final String code = "@unknown";
        rule.setCssText(code);

        final UnknownRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance", converted);
        Assert.assertEquals("Converted rule code", code, converted.getCode());
    }
}
