package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import org.junit.Assert;
import org.junit.Test;

import com.steadystate.css.dom.CSSImportRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;

public final class ImportRuleConverterTest {

    @Test
    public void getSupportedType_returnsCSSImportRuleImplClass() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        Assert.assertEquals("Supported type", CSSImportRuleImpl.class, converter.getSupportedType());
    }

    @Test
    public void convert() {
        final ImportRuleConverter converter = new ImportRuleConverter();
        final CSSImportRuleImpl rule = new CSSImportRuleImpl();
        final String code = "@import 'file.css';";
        rule.setCssText(code);

        final SimpleRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance", converted);
        Assert.assertEquals("Converted rule code", "@import url(file.css);", converted.getCode());
    }
}
