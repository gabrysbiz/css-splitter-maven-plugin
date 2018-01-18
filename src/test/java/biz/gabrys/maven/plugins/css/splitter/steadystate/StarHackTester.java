package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

final class StarHackTester {

    private final Standard standard;
    private final boolean starHackAllowed;

    public StarHackTester(final Standard standard, final boolean starHackAllowed) {
        this.standard = standard;
        this.starHackAllowed = starHackAllowed;
    }

    public StyleSheet parse() {
        final StringBuilder css = new StringBuilder();
        css.append("div {\n");
        css.append(" width: 0;\n");
        css.append(" *width: 0;\n");
        css.append(" height: 0;\n");
        css.append("}\n");

        final SteadyStateParser parser = new SteadyStateParser(mock(Log.class));
        final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).withStarHack(starHackAllowed).create();
        return parser.parse(css.toString(), options);
    }

    public void verify(final StyleSheet stylesheet) {
        final List<NodeRule> rules = stylesheet.getRules();
        assertThat(rules).hasSize(1);
        assertThat(rules.get(0)).isExactlyInstanceOf(StyleRule.class);

        final StyleRule styleRule = (StyleRule) rules.get(0);
        final List<StyleProperty> properties = styleRule.getProperties();
        assertThat(properties).hasSize(3);
        final StyleProperty property1 = properties.get(0);
        assertThat(property1).isNotNull();
        assertThat(property1.getCode()).isEqualTo("width: 0;");
        final StyleProperty property2 = properties.get(1);
        assertThat(property2).isNotNull();
        assertThat(property2.getCode()).isEqualTo("*width: 0;");
        final StyleProperty property3 = properties.get(2);
        assertThat(property3).isNotNull();
        assertThat(property3.getCode()).isEqualTo("height: 0;");
    }
}
