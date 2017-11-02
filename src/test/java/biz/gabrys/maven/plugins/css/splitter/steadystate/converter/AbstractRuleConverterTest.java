package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.steadystate.css.dom.CSSCharsetRuleImpl;
import com.steadystate.css.dom.CSSFontFaceRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class AbstractRuleConverterTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(null);
        assertFalse(supported);
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSFontFaceRuleImpl());
        assertFalse(supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSCharsetRuleImpl());
        assertTrue(supported);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void convert_typeIsInvalid_throwsException() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        converter.convert(new CSSFontFaceRuleImpl());
    }

    @Test
    public void convert_typeIsValid_executesConvert2() {
        final RuleConverterImpl converter = spy(new RuleConverterImpl());

        final CSSCharsetRuleImpl rule = new CSSCharsetRuleImpl();
        converter.convert(rule);

        verify(converter).convert2(rule);
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
