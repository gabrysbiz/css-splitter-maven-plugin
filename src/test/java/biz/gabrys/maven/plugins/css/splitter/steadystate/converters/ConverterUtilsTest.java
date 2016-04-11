package biz.gabrys.maven.plugins.css.splitter.steadystate.converters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.dom.CSSCharsetRuleImpl;
import com.steadystate.css.dom.CSSImportRuleImpl;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class ConverterUtilsTest {

    @Test(expected = UnsupportedRuleException.class)
    public void convert_convertersNotSupport_throwsException() {
        final CSSRule rule = new CSSRule() {
            public void setCssText(final String cssText) {
                // do nothing
            }

            public short getType() {
                return 0;
            }

            public CSSStyleSheet getParentStyleSheet() {
                return null;
            }

            public CSSRule getParentRule() {
                return null;
            }

            public String getCssText() {
                return "css";
            }
        };
        final List<RuleConverter<?, ?>> converters = new ArrayList<RuleConverter<?, ?>>();
        converters.add(new RuleConverter<CSSCharsetRuleImpl, StyleRule>() {

            public Class<CSSCharsetRuleImpl> getSupportedType() {
                return CSSCharsetRuleImpl.class;
            }

            public StyleRule convert(final CSSRule rule) {
                Assert.fail("Convert method should not be called!");
                return null;
            }
        });
        ConverterUtils.convert(rule, converters);
    }

    @Test
    public void convert_oneConverterSupport_returnsRule() {
        final CSSRule rule = new CSSImportRuleImpl();
        final List<RuleConverter<?, ?>> converters = new ArrayList<RuleConverter<?, ?>>();
        converters.add(new RuleConverter<CSSCharsetRuleImpl, StyleRule>() {

            public Class<CSSCharsetRuleImpl> getSupportedType() {
                return CSSCharsetRuleImpl.class;
            }

            public StyleRule convert(final CSSRule rule) {
                Assert.fail("Convert method should not be called!");
                return null;
            }
        });
        converters.add(new RuleConverter<CSSImportRuleImpl, SimpleRule>() {

            public Class<CSSImportRuleImpl> getSupportedType() {
                return CSSImportRuleImpl.class;
            }

            public SimpleRule convert(final CSSRule rule) {
                return new SimpleRule(rule.getCssText());
            }
        });
        final NodeRule converted = ConverterUtils.convert(rule, converters);
        Assert.assertNotNull("Converted rule instance", converted);
        Assert.assertEquals("Converted rule class", SimpleRule.class, converted.getClass());
    }

    @Test
    public void convert_twoConvertersSupportTheSameType_returnsRuleConvertedByFirstConverter() {
        final CSSRule rule = new CSSImportRuleImpl();
        final List<RuleConverter<?, ?>> converters = new ArrayList<RuleConverter<?, ?>>();
        converters.add(new RuleConverter<CSSImportRuleImpl, SimpleRule>() {

            public Class<CSSImportRuleImpl> getSupportedType() {
                return CSSImportRuleImpl.class;
            }

            public SimpleRule convert(final CSSRule rule) {
                return new SimpleRule(rule.getCssText());
            }
        });
        converters.add(new RuleConverter<CSSImportRuleImpl, SimpleRule>() {

            public Class<CSSImportRuleImpl> getSupportedType() {
                return CSSImportRuleImpl.class;
            }

            public SimpleRule convert(final CSSRule rule) {
                Assert.fail("Convert method should not be called!");
                return null;
            }
        });
        final NodeRule converted = ConverterUtils.convert(rule, converters);
        Assert.assertNotNull("Converted rule instance", converted);
        Assert.assertEquals("Converted rule class", SimpleRule.class, converted.getClass());
    }
}
