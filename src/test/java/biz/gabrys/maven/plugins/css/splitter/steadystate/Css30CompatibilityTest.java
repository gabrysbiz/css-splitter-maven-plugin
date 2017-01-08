package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class Css30CompatibilityTest {

    private static final Standard STANDARD = Standard.VERSION_3_0;
    private static final ParserOptions options = new ParserOptionsBuilder().withStandard(STANDARD).withStrict(false).create();

    @Test(expected = ParserException.class)
    public void parse_mediaStoresCharsetRule_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        parser.parse("@media all { @charset 'UTF-8'; }", options);
    }

    @Test(expected = ParserException.class)
    public void parse_mediaStoresFontFaceRule_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        parser.parse("@media all { @font-face { font-family: Arial; } }", options);
    }

    @Test(expected = ParserException.class)
    public void parse_mediaStoresImportRule_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        parser.parse("@media all { @import 'file.css'; }", options);
    }

    @Test
    public void parse_mediaStoresMediaRule_supported() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { @media all { } }", options);

        final ComplexRule media = (ComplexRule) stylesheet.getRules().get(0);
        Assert.assertEquals("Stylesheet nested rule class.", ComplexRule.class, media.getRules().get(0).getClass());
    }

    @Test
    public void parse_mediaStoresPageRule_supported() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { @page { size: 8.5; } }", options);

        final ComplexRule media = (ComplexRule) stylesheet.getRules().get(0);
        Assert.assertEquals("Stylesheet nested rule class.", StyleRule.class, media.getRules().get(0).getClass());
    }

    @Test
    public void parse_mediaStoresStyleRule_supported() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { style { width: 1px; }}", options);

        final ComplexRule media = (ComplexRule) stylesheet.getRules().get(0);
        Assert.assertEquals("Stylesheet nested rule class.", StyleRule.class, media.getRules().get(0).getClass());
    }

    @Test
    public void parse_mediaStoresUnknowRule_supported() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        final StyleSheet stylesheet = parser.parse("@media all { @unknown { size: 1px; } }", options);

        final ComplexRule media = (ComplexRule) stylesheet.getRules().get(0);
        Assert.assertEquals("Stylesheet nested rule class.", UnknownRule.class, media.getRules().get(0).getClass());
    }

    @Test(expected = ParserException.class)
    public void parse_starHackIsDisabled_throwsException() {
        new StarHackTester(STANDARD, false).parse();
    }

    @Test
    public void parse_starHackIsEnabled_returnsStyleSheet() {
        final StarHackTester tester = new StarHackTester(STANDARD, true);
        final StyleSheet stylesheet = tester.parse();
        Assert.assertNotNull("Stylesheet.", stylesheet);
        tester.verify(stylesheet);
    }
}
