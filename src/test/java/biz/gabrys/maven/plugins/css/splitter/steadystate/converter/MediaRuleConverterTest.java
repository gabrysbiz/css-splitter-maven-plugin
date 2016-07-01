package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.MediaListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class MediaRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final MediaRuleConverter converter = new MediaRuleConverter(true);
        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final boolean supported = converter.isSupportedType(rule);
        Assert.assertTrue("Should return true.", supported);
    }

    @Test
    public void convert() {
        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final MediaListImpl mediaList = new MediaListImpl();
        final String selector = "screen";
        mediaList.appendMedium(selector);
        rule.setMedia(mediaList);

        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        ruleList.add(styleRule);
        rule.setCssRules(ruleList);

        @SuppressWarnings("unchecked")
        final RuleConverter<NodeRule> internalConverter = Mockito.mock(RuleConverter.class);
        final NodeRule convertedRule = Mockito.mock(NodeRule.class);
        Mockito.when(internalConverter.isSupportedType(styleRule)).thenReturn(true);
        Mockito.when(internalConverter.convert(styleRule)).thenReturn(convertedRule);
        final MediaRuleConverter converter = new MediaRuleConverter(internalConverter);

        final ComplexRule converted = converter.convert(rule);
        Assert.assertNotNull("Converted rule instance.", converted);
        final List<String> selectors = converted.getSelectors();
        Assert.assertNotNull("Converted rule selectors instance.", selectors);
        Assert.assertEquals("Converted rule selectors size.", 1, selectors.size());
        Assert.assertEquals("Converted rule selector.", selector, selectors.get(0));

        final List<NodeRule> rules = converted.getRules();
        Assert.assertNotNull("Converted rule children.", rules);
        Assert.assertEquals("Converted rule children size.", 1, rules.size());
    }
}
