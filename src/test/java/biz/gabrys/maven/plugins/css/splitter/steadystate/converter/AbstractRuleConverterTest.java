package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.steadystate.css.dom.CSSCharsetRuleImpl;
import com.steadystate.css.dom.CSSFontFaceRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class AbstractRuleConverterTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(null);
        Assert.assertFalse("Should return false for null.", supported);
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSFontFaceRuleImpl());
        Assert.assertFalse("Should return false for invalid rule.", supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSCharsetRuleImpl());
        Assert.assertTrue("Should return false for valid rule.", supported);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_typeIsInvalid_throwsException() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        converter.convert(new CSSFontFaceRuleImpl());
    }

    @Test
    public void convert_typeIsValid_executesConvert2() {
        final RuleConverterImpl converter = Mockito.spy(new RuleConverterImpl());

        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        converter.convert(rule);

        Mockito.verify(converter).convert2(rule);
    }

    private static class RuleConverterImpl extends AbstractRuleConverter<CSSCharsetRuleImpl, NodeRule> {

        RuleConverterImpl() {
            super(CSSCharsetRuleImpl.class);
        }

        @Override
        protected NodeRule convert2(final CSSCharsetRuleImpl rule) {
            // do nothing
            return null;
        }
    }
}
