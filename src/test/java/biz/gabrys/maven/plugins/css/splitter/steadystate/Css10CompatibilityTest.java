package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class Css10CompatibilityTest {

    private static final Standard STANDARD = Standard.VERSION_1_0;
    private static final ParserOptions options = new ParserOptionsBuilder().withStandard(STANDARD).withStrict(false).create();

    @Test
    public void parse_mediaRule_returnsUnknown() {
        final Log logger = mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { rule { width: 100px; } }", options);

        assertEquals(UnknownRule.class, stylesheet.getRules().get(0).getClass());
    }

    @Test(expected = ParserException.class)
    public void parse_starHackIsDisabled_throwsException() {
        new StarHackTester(STANDARD, false).parse();
    }

    @Test(expected = ParserException.class)
    public void parse_starHackIsEnabled_throwsException() {
        new StarHackTester(STANDARD, true).parse();
    }
}
