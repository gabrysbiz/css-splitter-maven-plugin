package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.MediaListImpl;
import com.steadystate.css.parser.media.MediaQuery;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public final class MediaRuleConverterTest {

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleConverter<?> ruleConverter = mock(RuleConverter.class);
        final CssFormatter formatter = mock(CssFormatter.class);
        final MediaRuleConverter converter = new MediaRuleConverter(ruleConverter, formatter);

        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final boolean supported = converter.isSupportedType(rule);

        assertThat(supported).isTrue();
        verifyZeroInteractions(ruleConverter, formatter);
    }

    @Test
    public void convert() {
        final CSSMediaRuleImpl rule = new CSSMediaRuleImpl();
        final MediaListImpl mediaList = mock(MediaListImpl.class);
        when(mediaList.getLength()).thenReturn(1);
        final MediaQuery mediaQuery = mock(MediaQuery.class);
        when(mediaList.mediaQuery(0)).thenReturn(mediaQuery);
        rule.setMedia(mediaList);

        final CssFormatter formatter = mock(CssFormatter.class);
        final String selector = "screen";
        when(formatter.format(mediaQuery)).thenReturn(selector);

        final CSSRuleListImpl ruleList = new CSSRuleListImpl();
        final CSSStyleRuleImpl styleRule = new CSSStyleRuleImpl();
        ruleList.add(styleRule);
        rule.setCssRules(ruleList);

        @SuppressWarnings("unchecked")
        final RuleConverter<NodeRule> internalConverter = mock(RuleConverter.class);
        final NodeRule convertedRule = mock(NodeRule.class);
        when(internalConverter.isSupportedType(styleRule)).thenReturn(true);
        when(internalConverter.convert(styleRule)).thenReturn(convertedRule);

        final MediaRuleConverter converter = new MediaRuleConverter(internalConverter, formatter);

        final ComplexRule converted = converter.convert(rule);
        assertThat(converted).isNotNull();
        assertThat(converted.getSelectors()).containsExactly(selector);
        assertThat(converted.getRules()).hasSize(1);
    }

    @Test
    @Parameters(method = "allStandardsExceptCSS3")
    public void createConverter_strictAndNotCSS3_returnsStyleRuleConverter(final Standard standard) {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);
        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, true);
        assertThat(converter).isExactlyInstanceOf(StyleRuleConverter.class);
        verifyZeroInteractions(thisObject);
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
    @Parameters(method = "allStandardsExceptCSS3")
    public void createConverter_notStrictAndNotCSS3_returnsMultipleRuleConverterWith3Subconverters(final Standard standard) {
        final MediaRuleConverter thisObject = mock(MediaRuleConverter.class);

        final RuleConverter<?> converter = MediaRuleConverter.createConverter(thisObject, standard, false);

        assertThat(converter).isExactlyInstanceOf(MultipleRuleConverter.class);
        final MultipleRuleConverter ruleConverter = (MultipleRuleConverter) converter;
        assertThat(ruleConverter.converters).hasSize(3);
        assertThat(ruleConverter.converters.get(0)).isExactlyInstanceOf(StyleRuleConverter.class);
        assertThat(ruleConverter.converters.get(1)).isExactlyInstanceOf(PageRuleConverter.class);
        assertThat(ruleConverter.converters.get(2)).isExactlyInstanceOf(UnknownRuleConverter.class);
        verifyZeroInteractions(thisObject);
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

    public static Collection<Standard> allStandardsExceptCSS3() {
        final Collection<Standard> standards = new ArrayList<Standard>();
        for (final Standard standard : Standard.values()) {
            if (standard != Standard.VERSION_3_0) {
                standards.add(standard);
            }
        }
        return standards;
    }
}
