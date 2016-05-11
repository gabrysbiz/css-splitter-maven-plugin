package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class Css10CompatibilityTest {

    private final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_1_0).withStrict(false).create();

    @Test
    public void parse_mediaRule_returnsUnknown() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { rule { width: 100px; } }", options);

        Assert.assertEquals("@media rule class.", UnknownRule.class, stylesheet.getRules().get(0).getClass());
    }
}
