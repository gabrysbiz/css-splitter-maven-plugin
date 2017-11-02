package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

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
        final RuleConverter<?> ruleConverter = mock(RuleConverter.class);
        final MediaRuleConverter converter = new MediaRuleConverter(ruleConverter);

        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final boolean supported = converter.isSupportedType(rule);

        assertTrue(supported);
        verifyZeroInteractions(ruleConverter);
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
        final RuleConverter<NodeRule> internalConverter = mock(RuleConverter.class);
        final NodeRule convertedRule = mock(NodeRule.class);
        when(internalConverter.isSupportedType(styleRule)).thenReturn(true);
        when(internalConverter.convert(styleRule)).thenReturn(convertedRule);
        final MediaRuleConverter converter = new MediaRuleConverter(internalConverter);

        final ComplexRule converted = converter.convert(rule);
        assertNotNull("Converted rule instance should not be equal to null", converted);
        final List<String> selectors = converted.getSelectors();
        assertNotNull("Converted rule selectors instance should not be equal to null", selectors);
        assertEquals("Converted rule selectors size", 1, selectors.size());
        assertEquals("Converted rule selector", selector, selectors.get(0));

        final List<NodeRule> rules = converted.getRules();
        assertNotNull("Converted rule children should not be equal to null", rules);
        assertEquals("Converted rule children size", 1, rules.size());
    }

    @Test
    public void createConverter_strictAndNotCSS3_returnsStyleRuleConverter() {
        for (final Standard standard : Standard.values()) {
            if (Standard.VERSION_3_0 == standard) {
                continue;
            }
            final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
            final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, true);
            assertNotNull(String.format("Converter instance for standard %s should not be equal to null", standard), converter);
            assertEquals(String.format("Converter class for standard %s", standard), StyleRuleConverter.class, converter.getClass());
            verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_strictAndCSS3_returnsMultipleRuleConverterWith2Subconverters() {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, true);
        assertNotNull("Converter instance should not be equal to null", converter);
        assertEquals("Converter class", MultipleRuleConverter.class, converter.getClass());

        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        final List<RuleConverter<?>> children = ruleConverter.converters;
        assertEquals("Converters quantity", 2, children.size());
        assertEquals("First converter class", StyleRuleConverter.class, children.get(0).getClass());
        assertEquals("Second converter instance", thisObject, children.get(1));
        verifyZeroInteractions(thisObject);
    }

    @Test
    public void createConverter_notStrictAndNotCSS3_returnsMultipleRuleConverterWith3Subconverters() {
        for (final Standard standard : Standard.values()) {
            if (Standard.VERSION_3_0 == standard) {
                continue;
            }
            final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
            final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, false);
            assertNotNull(String.format("Converter instance for standard %s should not be equal to null", standard), converter);
            assertEquals(String.format("Converter class for standard %s", standard), MultipleRuleConverter.class, converter.getClass());

            final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
            final List<RuleConverter<?>> children = ruleConverter.converters;
            assertEquals(String.format("Converters quantity for standard %s", standard), 3, children.size());
            assertEquals(String.format("First converter class for standard %s", standard), StyleRuleConverter.class,
                    children.get(0).getClass());
            assertEquals(String.format("Second converter class for standard %s", standard), PageRuleConverter.class,
                    children.get(1).getClass());
            assertEquals(String.format("Third converter class for standard %s", standard), UnknownRuleConverter.class,
                    children.get(2).getClass());
            verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_notStrictAndCSS3_returnsMultipleRuleConverterWith4Subconverters() {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, false);
        assertNotNull("Converter instance should not be equal to null", converter);
        assertEquals("Converter class", MultipleRuleConverter.class, converter.getClass());

        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        final List<RuleConverter<?>> children = ruleConverter.converters;
        assertEquals("Converters quantity", 4, children.size());
        assertEquals("First converter class", StyleRuleConverter.class, children.get(0).getClass());
        assertEquals("Second converter instance", thisObject, children.get(1));
        assertEquals("Third converter class", PageRuleConverter.class, children.get(2).getClass());
        assertEquals("Fourth converter class", UnknownRuleConverter.class, children.get(3).getClass());
        verifyZeroInteractions(thisObject);
    }
}
