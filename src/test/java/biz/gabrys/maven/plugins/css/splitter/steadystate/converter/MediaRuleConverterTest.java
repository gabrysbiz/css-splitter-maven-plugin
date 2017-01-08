package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.MediaListImpl;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class MediaRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleConverter<?> ruleConverter = Mockito.mock(RuleConverter.class);
        final MediaRuleConverter converter = new MediaRuleConverter(ruleConverter);

        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final boolean supported = converter.isSupportedType(rule);

        Assert.assertTrue("Should return true.", supported);
        Mockito.verifyZeroInteractions(ruleConverter);
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

    @Test
    public void createConverter_strictAndNotCSS3_returnsStyleRuleConverter() {
        for (final Standard standard : Standard.values()) {
            if (Standard.VERSION_3_0 == standard) {
                continue;
            }
            final MediaRuleConverter thisObject = Mockito.mock(MediaRuleConverter.class);
            final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, true);
            Assert.assertNotNull(String.format("Converter instance for standard %s.", standard), converter);
            Assert.assertEquals(String.format("Converter class for standard %s.", standard), StyleRuleConverter.class,
                    converter.getClass());
            Mockito.verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_strictAndCSS3_returnsMultipleRuleConverterWith2Subconverters() {
        final MediaRuleConverter thisObject = Mockito.mock(MediaRuleConverter.class);
        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, true);
        Assert.assertNotNull("Converter instance.", converter);
        Assert.assertEquals("Converter class.", MultipleRuleConverter.class, converter.getClass());

        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        final List<RuleConverter<?>> children = ruleConverter.converters;
        Assert.assertEquals("Converters quantity.", 2, children.size());
        Assert.assertEquals("First converter class.", StyleRuleConverter.class, children.get(0).getClass());
        Assert.assertEquals("Second converter instance.", thisObject, children.get(1));
        Mockito.verifyZeroInteractions(thisObject);
    }

    @Test
    public void createConverter_notStrictAndNotCSS3_returnsMultipleRuleConverterWith3Subconverters() {
        for (final Standard standard : Standard.values()) {
            if (Standard.VERSION_3_0 == standard) {
                continue;
            }
            final MediaRuleConverter thisObject = Mockito.mock(MediaRuleConverter.class);
            final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, false);
            Assert.assertNotNull(String.format("Converter instance for standard %s.", standard), converter);
            Assert.assertEquals(String.format("Converter class for standard %s.", standard), MultipleRuleConverter.class,
                    converter.getClass());

            final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
            final List<RuleConverter<?>> children = ruleConverter.converters;
            Assert.assertEquals(String.format("Converters quantity for standard %s.", standard), 3, children.size());
            Assert.assertEquals(String.format("First converter class for standard %s.", standard), StyleRuleConverter.class,
                    children.get(0).getClass());
            Assert.assertEquals(String.format("Second converter class for standard %s.", standard), PageRuleConverter.class,
                    children.get(1).getClass());
            Assert.assertEquals(String.format("Third converter class for standard %s.", standard), UnknownRuleConverter.class,
                    children.get(2).getClass());
            Mockito.verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_notStrictAndCSS3_returnsMultipleRuleConverterWith4Subconverters() {
        final MediaRuleConverter thisObject = Mockito.mock(MediaRuleConverter.class);
        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, false);
        Assert.assertNotNull("Converter instance.", converter);
        Assert.assertEquals("Converter class.", MultipleRuleConverter.class, converter.getClass());

        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        final List<RuleConverter<?>> children = ruleConverter.converters;
        Assert.assertEquals("Converters quantity.", 4, children.size());
        Assert.assertEquals("First converter class.", StyleRuleConverter.class, children.get(0).getClass());
        Assert.assertEquals("Second converter instance.", thisObject, children.get(1));
        Assert.assertEquals("Third converter class.", PageRuleConverter.class, children.get(2).getClass());
        Assert.assertEquals("Fourth converter class.", UnknownRuleConverter.class, children.get(3).getClass());
        Mockito.verifyZeroInteractions(thisObject);
    }
}
