package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSFontFaceRuleImpl());
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleConverterImpl converter = new RuleConverterImpl();
        final boolean supported = converter.isSupportedType(new CSSCharsetRuleImpl());
        assertThat(supported).isTrue();
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
            return null;
        }
    }
}
