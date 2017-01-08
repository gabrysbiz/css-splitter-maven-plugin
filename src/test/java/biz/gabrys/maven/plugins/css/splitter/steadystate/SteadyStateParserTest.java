package biz.gabrys.maven.plugins.css.splitter.steadystate;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_3_0).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        Assert.assertNotNull("StyleSheet instance.", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        Assert.assertNotNull("StyleSheet rules instnace.", rules);
        Assert.assertEquals("StyleSheet children rules.", 8, rules.size());

        final Iterator<NodeRule> iterator = rules.iterator();
        Assert.assertEquals("@charset rule instance class.", SimpleRule.class, iterator.next().getClass());
        Assert.assertEquals("@import rule instance class.", SimpleRule.class, iterator.next().getClass());
        Assert.assertEquals("Style rule instance class.", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@media rule instance class.", ComplexRule.class, iterator.next().getClass());
        Assert.assertEquals("@font-face rule instance class.", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@page rule instance class.", StyleRule.class, iterator.next().getClass());
        Assert.assertEquals("@unknown rule instance class.", UnknownRule.class, iterator.next().getClass());
        Assert.assertEquals("@keyframes rule instance class.", UnknownRule.class, iterator.next().getClass());
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

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_3_0).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        Assert.assertNotNull("StyleSheet instance.", stylesheet);
        final List<NodeRule> rules = stylesheet.getRules();
        Assert.assertNotNull("StyleSheet rules instnace.", rules);
        Assert.assertEquals("StyleSheet children rules.", 1, rules.size());
        final NodeRule rule = rules.get(0);
        Assert.assertEquals("StyleSheet children rule class.", StyleRule.class, rule.getClass());
        final StyleRule fontFaceRule = (StyleRule) rule;
        final List<String> selectors = fontFaceRule.getSelectors();
        Assert.assertEquals("FontFace rule selectors count.", 1, selectors.size());
        Assert.assertEquals("FontFace rule selector.", "@font-face", selectors.get(0));
        final List<StyleProperty> properties = fontFaceRule.getProperties();
        Assert.assertEquals("FontFace style properties.", 5, properties.size());
        StyleProperty property = properties.get(0);
        Assert.assertEquals("FontFace first style property name.", "font-family", property.getName());
        Assert.assertEquals("FontFace first style property value.", "FontAwesome", property.getValue());
        property = properties.get(1);
        Assert.assertEquals("FontFace first style property name.", "src", property.getName());
        Assert.assertEquals("FontFace first style property value.", "url(../base/fonts/fontawesome-webfont.eot)", property.getValue());
        property = properties.get(2);
        Assert.assertEquals("FontFace first style property name.", "src", property.getName());
        Assert.assertEquals("FontFace first style property value.", value.toString(), property.getValue());
        property = properties.get(3);
        Assert.assertEquals("FontFace first style property name.", "font-weight", property.getName());
        Assert.assertEquals("FontFace first style property value.", "normal", property.getValue());
        property = properties.get(4);
        Assert.assertEquals("FontFace first style property name.", "font-style", property.getName());
        Assert.assertEquals("FontFace first style property value.", "normal", property.getValue());
    }

    @Test
    public void parse_documentContainsPropertiesWithStarHackButSupportIsDisabled_throwsException() {
        final StringBuilder css = new StringBuilder();
        css.append("div {\n");
        css.append(" width: 0;\n");
        css.append(" *width: 0;\n");
        css.append(" height: 0;\n");
        css.append("}\n");

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));

        for (final Standard standard : Standard.values()) {
            final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).withStarHack(false).create();

            try {
                parser.parse(css.toString(), options);
                Assert.fail(String.format("Should throw exception for standard %s.", standard));
            } catch (final ParserException e) {
                // ok
            }
        }
    }

    @Test
    public void parse_documentContainsPropertiesWithStarHackAndSupportIsEnabled_returnsStyleShees() {
        final StringBuilder css = new StringBuilder();
        css.append("div {\n");
        css.append(" width: 0px;\n");
        css.append(" *width: 0px;\n");
        css.append(" height: 0px;\n");
        css.append("}\n");

        final SteadyStateParser parser = new SteadyStateParser(Mockito.mock(Log.class));

        // TODO: does not work with CSS 1.0 and 2.0 -> https://sourceforge.net/p/cssparser/bugs/62/
        // for (final Standard standard : Standard.values()) {
        for (final Standard standard : Arrays.asList(Standard.VERSION_3_0, Standard.VERSION_2_1)) {
            final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).withStarHack(true).create();

            final StyleSheet stylesheet;
            try {
                stylesheet = parser.parse(css.toString(), options);
            } catch (final ParserException e) {
                Assert.fail(String.format("Parser cannot parse stylesheet for standard %s.", standard));
                return;
            }
            Assert.assertNotNull(String.format("StyleSheet instance for standard %s.", standard), stylesheet);
            final List<NodeRule> rules = stylesheet.getRules();
            Assert.assertNotNull(String.format("StyleSheet rules instnace for standard %s.", standard), rules);
            Assert.assertEquals(String.format("StyleSheet children rules for standard %s.", standard), 1, rules.size());
            Assert.assertEquals(String.format("First child class for standard %s.", standard), StyleRule.class, rules.get(0).getClass());

            final StyleRule styleRule = (StyleRule) rules.get(0);
            final List<StyleProperty> properties = styleRule.getProperties();
            Assert.assertEquals(String.format("Properties quantity for standard %s.", standard), 3, properties.size());
            Assert.assertEquals(String.format("First property code for standard %s.", standard), "width: 0;", properties.get(0).getCode());
            Assert.assertEquals(String.format("Second property code for standard %s.", standard), "*width: 0;",
                    properties.get(1).getCode());
            Assert.assertEquals(String.format("Third property code for standard %s.", standard), "height: 0;", properties.get(2).getCode());
        }
    }
}
