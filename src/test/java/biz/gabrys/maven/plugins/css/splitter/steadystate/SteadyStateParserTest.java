package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.junit.runner.RunWith;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
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

        assertThat(stylesheet).isNotNull();
        final List<NodeRule> rules = stylesheet.getRules();
        assertThat(rules).hasSize(8);
        assertThat(rules.get(0)).isExactlyInstanceOf(SimpleRule.class);
        assertThat(rules.get(1)).isExactlyInstanceOf(SimpleRule.class);
        assertThat(rules.get(2)).isExactlyInstanceOf(StyleRule.class);
        assertThat(rules.get(3)).isExactlyInstanceOf(ComplexRule.class);
        assertThat(rules.get(4)).isExactlyInstanceOf(StyleRule.class);
        assertThat(rules.get(5)).isExactlyInstanceOf(StyleRule.class);
        assertThat(rules.get(6)).isExactlyInstanceOf(UnknownRule.class);
        assertThat(rules.get(7)).isExactlyInstanceOf(UnknownRule.class);
    }

    // https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/3#issuecomment-194338886
    @Test
    public void parse_fontFaceRuleFromIssueNo3_parsesCorrectly() {
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

        assertThat(stylesheet).isNotNull();
        final List<NodeRule> rules = stylesheet.getRules();
        assertThat(rules).hasSize(1);

        final NodeRule rule = rules.get(0);
        assertThat(rule).isExactlyInstanceOf(StyleRule.class);
        final StyleRule fontFaceRule = (StyleRule) rule;
        assertThat(fontFaceRule.getSelectors()).containsExactly("@font-face");

        final List<StyleProperty> properties = fontFaceRule.getProperties();
        assertThat(properties).hasSize(5);
        verifyProperty(properties.get(0), "font-family", "FontAwesome");
        verifyProperty(properties.get(1), "src", "url(../base/fonts/fontawesome-webfont.eot)");
        verifyProperty(properties.get(2), "src", value.toString());
        verifyProperty(properties.get(3), "font-weight", "normal");
        verifyProperty(properties.get(4), "font-style", "normal");
    }

    private static void verifyProperty(final StyleProperty property, final String name, final String value) {
        assertThat(property).isNotNull();
        assertThat(property.getName()).isEqualTo(name);
        assertThat(property.getValue()).isEqualTo(value);
    }

    // https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/23
    @Test
    @Parameters(method = "allStandards")
    public void parse_useImportant_importantIsPreserved(final Standard standard) {
        final StringBuilder css = new StringBuilder();
        css.append("missing-important {");
        css.append("\twidth: 100px !important;");
        css.append("}");

        final SteadyStateParser parser = new SteadyStateParser(mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        assertThat(stylesheet).isNotNull();
        final List<NodeRule> rules = stylesheet.getRules();
        assertThat(rules).hasSize(1);
        final NodeRule rule = rules.get(0);
        assertThat(rule).isExactlyInstanceOf(StyleRule.class);
        final StyleRule styleRule = (StyleRule) rule;
        assertThat(styleRule.getSelectors()).containsExactly("missing-important");

        final List<StyleProperty> properties = styleRule.getProperties();
        assertThat(properties).hasSize(1);
        final StyleProperty property = properties.get(0);
        assertThat(property).isNotNull();
        assertThat(property.getName()).isEqualTo("width");
        assertThat(property.getValue()).isEqualTo("100px");
        assertThat(property.isImportant()).isTrue();
        assertThat(property.getCode()).isEqualTo("width: 100px !important;");
    }

    // https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/26
    @Test
    @Parameters(method = "allStandards")
    public void parse_specialCharactersAreUsed_specialCharastersIsPreserved(final Standard standard) {
        final StringBuilder css = new StringBuilder();
        css.append("special-characters {");
        css.append("\tcontent: \"\\200B\\n\\t\\r\";");
        css.append("}");

        final SteadyStateParser parser = new SteadyStateParser(mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).withStrict(true).create();
        final StyleSheet stylesheet = parser.parse(css.toString(), options);

        assertThat(stylesheet).isNotNull();
        final List<NodeRule> rules = stylesheet.getRules();
        assertThat(rules).hasSize(1);
        final NodeRule rule = rules.get(0);
        assertThat(rule).isExactlyInstanceOf(StyleRule.class);
        final StyleRule styleRule = (StyleRule) rule;
        assertThat(styleRule.getSelectors()).containsExactly("special-characters");

        final List<StyleProperty> properties = styleRule.getProperties();
        assertThat(properties).hasSize(1);
        final StyleProperty property = properties.get(0);
        assertThat(property).isNotNull();
        assertThat(property.getName()).isEqualTo("content");
        assertThat(property.getValue()).isEqualTo("\"\\200B\\n\\t\\r\"");
        assertThat(property.getCode()).isEqualTo("content: \"\\200B\\n\\t\\r\";");
    }

    public static Standard[] allStandards() {
        return Standard.values();
    }
}
