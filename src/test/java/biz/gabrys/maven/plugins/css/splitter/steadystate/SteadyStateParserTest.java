package biz.gabrys.maven.plugins.css.splitter.steadystate;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class SteadyStateParserTest {

    @Test
    public void parse_documentWithDifferentRules_returnsStyleSheet() throws ParserException {
        final StringBuilder css = new StringBuilder();
        css.append("@charset 'UTF-8';\n");
        css.append("@import 'file.css';\n");
        css.append("rule {prop1: val1; prop2: val2; prop1: val3}\n");
        css.append("@media screen {subrule {prop:val}}\n");
        css.append("@font-face {font-family: Arial;}\n");
        css.append("@page :first {prop:val}\n");
        css.append("@unknown 'value';");

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));
        final StyleSheet stylesheet = parser.parse(css.toString(), Standard.VERSION_3_0);

        Assert.assertNotNull("StyleSheet instance", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        Assert.assertNotNull("StyleSheet rules instnace", rules);
        Assert.assertEquals("StyleSheet children rules", 7, rules.size());

        final Iterator<NodeRule> iterator = rules.iterator();
        Assert.assertEquals("@charset rule instance class", SimpleRule.class, iterator.next().getClass());
        Assert.assertEquals("@import rule instance class", SimpleRule.class, iterator.next().getClass());
        Assert.assertEquals("Style rule instance class", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@media rule instance class", ComplexRule.class, iterator.next().getClass());
        Assert.assertEquals("@font-face rule instance class", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@page rule instance class", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@unknown rule instance class", UnknownRule.class, iterator.next().getClass());
    }

    // https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/3#issuecomment-194338886
    @Test
    public void parse_fontFaceRuleFromIssueNo3_returnsStyleSheet() throws ParserException {
        final StringBuilder css = new StringBuilder();
        css.append("@font-face {");
        css.append("\tfont-family: FontAwesome;");
        css.append("\tsrc: url(../base/fonts/fontawesome-webfont.eot);");
        final StringBuilder value = new StringBuilder();
        value.append("url(../base/fonts/fontawesome-webfont.eot?#iefix) format(\"embedded-opentype\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.woff) format(\"woff\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.ttf) format(\"truetype\"),");
        value.append(" url(../base/fonts/fontawesome-webfont.svg) format(\"svg\")");
        css.append("\tsrc: ");
        css.append(value);
        css.append(';');
        css.append("\tfont-weight: normal;");
        css.append("\tfont-style: normal;");
        css.append("}");

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));
        final StyleSheet stylesheet = parser.parse(css.toString(), Standard.VERSION_3_0);

        Assert.assertNotNull("StyleSheet instance", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        Assert.assertNotNull("StyleSheet rules instnace", rules);
        Assert.assertEquals("StyleSheet children rules", 1, rules.size());
        final NodeRule rule = rules.get(0);
        Assert.assertEquals("StyleSheet children rule class", StyleRule.class, rule.getClass());
        final StyleRule fontFaceRule = (StyleRule) rule;
        final List<String> selectors = fontFaceRule.getSelectors();
        Assert.assertEquals("FontFace rule selectors count", 1, selectors.size());
        Assert.assertEquals("FontFace rule selector", "@font-face", selectors.get(0));
        final List<StyleProperty> properties = fontFaceRule.getProperties();
        Assert.assertEquals("FontFace style properties", 5, properties.size());
        StyleProperty property = properties.get(0);
        Assert.assertEquals("FontFace first style property name", "font-family", property.getName());
        Assert.assertEquals("FontFace first style property value", "FontAwesome", property.getValue());
        property = properties.get(1);
        Assert.assertEquals("FontFace first style property name", "src", property.getName());
        Assert.assertEquals("FontFace first style property value", "url(../base/fonts/fontawesome-webfont.eot)", property.getValue());
        property = properties.get(2);
        Assert.assertEquals("FontFace first style property name", "src", property.getName());
        Assert.assertEquals("FontFace first style property value", value.toString(), property.getValue());
        property = properties.get(3);
        Assert.assertEquals("FontFace first style property name", "font-weight", property.getName());
        Assert.assertEquals("FontFace first style property value", "normal", property.getValue());
        property = properties.get(4);
        Assert.assertEquals("FontFace first style property name", "font-style", property.getName());
        Assert.assertEquals("FontFace first style property value", "normal", property.getValue());
    }
}
