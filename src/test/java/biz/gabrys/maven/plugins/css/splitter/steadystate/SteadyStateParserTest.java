package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class SteadyStateParserTest {

    @Test
    public void parse_documentWithDifferentRules_returnsStyleSheet() {
        final StringBuilder css = new StringBuilder();
        css.append("@charset 'UTF-8';\n");
        css.append("@import 'file.css';\n");
        css.append("rule {prop1: val1; prop2: val2; prop1: val3}\n");
        css.append("@media screen {subrule {prop:val}}\n");
        css.append("@font-face {font-family: Arial;}\n");
        css.append("@page :first {prop:val}\n");
        css.append("@unknown 'value';\n");
        css.append("@keyframes mymove { 50% {top: 0px;} 100% {top: 100px;}");

        final SteadyStateParser parser = new SteadyStateParser(mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_3_0).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        assertNotNull("StyleSheet instance should not be equal to null", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        assertNotNull("StyleSheet rules instnace should not be equal to null", rules);
        assertEquals("StyleSheet children rules", 8, rules.size());

        final Iterator<NodeRule> iterator = rules.iterator();
        assertEquals("@charset rule instance class", SimpleRule.class, iterator.next().getClass());
        assertEquals("@import rule instance class", SimpleRule.class, iterator.next().getClass());
        assertEquals("Style rule instance class", StyleRule.class, iterator.next().getClass());
        assertEquals("@media rule instance class", ComplexRule.class, iterator.next().getClass());
        assertEquals("@font-face rule instance class", StyleRule.class, iterator.next().getClass());
        assertEquals("@page rule instance class", StyleRule.class, iterator.next().getClass());
        assertEquals("@unknown rule instance class", UnknownRule.class, iterator.next().getClass());
        assertEquals("@keyframes rule instance class", UnknownRule.class, iterator.next().getClass());
    }

    // https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/3#issuecomment-194338886
    @Test
    public void parse_fontFaceRuleFromIssueNo3_returnsStyleSheet() {
        final StringBuilder css = new StringBuilder();
        css.append("@font-face {");
        css.append("\tfont-family: FontAwesome;");
        css.append("\tsrc: url(../base/fonts/fontawesome-webfont.eot);");
        css.append("\tsrc: ");
        final StringBuilder value = new StringBuilder();
        value.append("url(../base/fonts/fontawesome-webfont.eot?#iefix) format(\"embedded-opentype\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.woff) format(\"woff\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.ttf) format(\"truetype\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.svg) format(\"svg\")");
        css.append(value);
        css.append(';');
        css.append("\tfont-weight: normal;");
        css.append("\tfont-style: normal;");
        css.append("}");

        final SteadyStateParser parser = new SteadyStateParser(mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_3_0).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        assertNotNull("StyleSheet instance should not be equal to null", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        assertNotNull("StyleSheet rules instnace should not be equal to null", rules);
        assertEquals("StyleSheet children rules", 1, rules.size());
        final NodeRule rule = rules.get(0);
        assertEquals("StyleSheet children rule class", StyleRule.class, rule.getClass());
        final StyleRule fontFaceRule = (StyleRule) rule;
        final List<String> selectors = fontFaceRule.getSelectors();
        assertEquals("FontFace rule selectors count", 1, selectors.size());
        assertEquals("FontFace rule selector", "@font-face", selectors.get(0));
        final List<StyleProperty> properties = fontFaceRule.getProperties();
        assertEquals("FontFace style properties", 5, properties.size());
        StyleProperty property = properties.get(0);
        assertEquals("FontFace first style property name", "font-family", property.getName());
        assertEquals("FontFace first style property value", "FontAwesome", property.getValue());
        property = properties.get(1);
        assertEquals("FontFace first style property name", "src", property.getName());
        assertEquals("FontFace first style property value", "url(../base/fonts/fontawesome-webfont.eot)", property.getValue());
        property = properties.get(2);
        assertEquals("FontFace first style property name", "src", property.getName());
        assertEquals("FontFace first style property value", value.toString(), property.getValue());
        property = properties.get(3);
        assertEquals("FontFace first style property name", "font-weight", property.getName());
        assertEquals("FontFace first style property value", "normal", property.getValue());
        property = properties.get(4);
        assertEquals("FontFace first style property name", "font-style", property.getName());
        assertEquals("FontFace first style property value", "normal", property.getValue());
    }
}
