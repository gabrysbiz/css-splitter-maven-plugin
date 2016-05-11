package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class Css20CompatibilityTest {

    private final ParserOptions options = new ParserOptionsBuilder().withStandard(Standard.VERSION_2_0).withStrict(false).create();

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

    @Test(expected = ParserException.class)
    public void parse_mediaStoresMediaRule_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final SteadyStateParser parser = new SteadyStateParser(logger);

        parser.parse("@media all { @media all { } }", options);
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
}
