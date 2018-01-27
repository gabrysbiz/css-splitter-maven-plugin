package biz.gabrys.maven.plugins.css.splitter.css;

import static org.mockito.Mockito.mock;

import org.apache.maven.plugin.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.steadystate.ParserOptions;
import biz.gabrys.maven.plugins.css.splitter.steadystate.ParserOptionsBuilder;
import biz.gabrys.maven.plugins.css.splitter.steadystate.SteadyStateParser;

public class PreserveSpecialCharacterSequencesTest {

    @Test
    public void testIfCharsArePreserved() {
        final String css = "div:after { content: \"css\\200Bparser\" }";

        final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_3_0).withStrict(true)
                                                                .withStarHack(false).create();
        final Log logger = mock(Log.class);
        final StyleSheet stylesheet = new SteadyStateParser(logger).parse(css, options);
        Assertions.assertThat(stylesheet.toString()).contains("\\200B");
    }
}
