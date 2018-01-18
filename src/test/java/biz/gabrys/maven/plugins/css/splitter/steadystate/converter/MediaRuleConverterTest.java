package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

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

        assertThat(supported).isTrue();
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
        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly(selector);
        assertThat(converted.getRules()).hasSize(1);
    }

    @Test
    public void createConverter_strictAndNotCSS3_returnsStyleRuleConverter() {
        for (final Standard standard : Standard.values()) {
            if (Standard.VERSION_3_0 == standard) {
                continue;
            }
            final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
            final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, true);
            assertThat(converter).overridingErrorMessage("Converter for standard %s should be an instance of StyleRuleConverter", standard)
                    .isExactlyInstanceOf(StyleRuleConverter.class);
            verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_strictAndCSS3_returnsMultipleRuleConverterWith2Subconverters() {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);

        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, true);

        assertThat(converter).isExactlyInstanceOf(MultipleRuleConverter.class);
        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        assertThat(ruleConverter.converters).hasSize(2);
        assertThat(ruleConverter.converters.get(0)).isExactlyInstanceOf(StyleRuleConverter.class);
        assertThat(ruleConverter.converters.get(1)).isSameAs(thisObject);
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

            assertThat(converter)
                    .overridingErrorMessage("Converter for standard %s should be an instance of MultipleRuleConverter", standard)
                    .isExactlyInstanceOf(MultipleRuleConverter.class);

            final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
            assertThat(ruleConverter.converters).hasSize(3);
            assertThat(ruleConverter.converters.get(0))
                    .overridingErrorMessage("First converter for standard %s should be an instance of StyleRuleConverter", standard)
                    .isExactlyInstanceOf(StyleRuleConverter.class);
            assertThat(ruleConverter.converters.get(1))
                    .overridingErrorMessage("Second converter for standard %s should be an instance of PageRuleConverter", standard)
                    .isExactlyInstanceOf(PageRuleConverter.class);
            assertThat(ruleConverter.converters.get(2))
                    .overridingErrorMessage("Third converter for standard %s should be an instance of UnknownRuleConverter", standard)
                    .isExactlyInstanceOf(UnknownRuleConverter.class);
            verifyZeroInteractions(thisObject);
        }
    }

    @Test
    public void createConverter_notStrictAndCSS3_returnsMultipleRuleConverterWith4Subconverters() {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);

        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, Standard.VERSION_3_0, false);

        assertThat(converter).isExactlyInstanceOf(MultipleRuleConverter.class);
        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        assertThat(ruleConverter.converters).hasSize(4);
        assertThat(ruleConverter.converters.get(0)).isExactlyInstanceOf(StyleRuleConverter.class);
        assertThat(ruleConverter.converters.get(1)).isSameAs(thisObject);
        assertThat(ruleConverter.converters.get(2)).isExactlyInstanceOf(PageRuleConverter.class);
        assertThat(ruleConverter.converters.get(3)).isExactlyInstanceOf(UnknownRuleConverter.class);
        verifyZeroInteractions(thisObject);
    }
}
