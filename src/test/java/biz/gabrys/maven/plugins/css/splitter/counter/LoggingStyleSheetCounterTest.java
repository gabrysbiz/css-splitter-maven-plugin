package biz.gabrys.maven.plugins.css.splitter.counter;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class LoggingStyleSheetCounterTest {

    @Test
    public void count_zeroRules() {
        final StyleSheetCounter internalCounter = Mockito.mock(StyleSheetCounter.class);
        final Log logger = Mockito.mock(Log.class);
        final LoggingStyleSheetCounter counter = new LoggingStyleSheetCounter(internalCounter, logger);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final int count = 0;
        Mockito.when(internalCounter.count(stylesheet)).thenReturn(count);

        Assert.assertEquals("Should return 0.", count, counter.count(stylesheet));
        Mockito.verify(internalCounter).count(stylesheet);
        Mockito.verify(logger).info("Stylesheet contains 0 rules.");
        Mockito.verifyNoMoreInteractions(internalCounter, logger);
    }

    @Test
    public void count_oneRule() {
        final StyleSheetCounter internalCounter = Mockito.mock(StyleSheetCounter.class);
        final Log logger = Mockito.mock(Log.class);
        final LoggingStyleSheetCounter counter = new LoggingStyleSheetCounter(internalCounter, logger);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final int count = 1;
        Mockito.when(internalCounter.count(stylesheet)).thenReturn(count);

        Assert.assertEquals("Should return 1.", count, counter.count(stylesheet));
        Mockito.verify(internalCounter).count(stylesheet);
        Mockito.verify(logger).info("Stylesheet contains 1 rule.");
        Mockito.verifyNoMoreInteractions(internalCounter, logger);
    }

    @Test
    public void count_moreRulesThanOne() {
        final StyleSheetCounter internalCounter = Mockito.mock(StyleSheetCounter.class);
        final Log logger = Mockito.mock(Log.class);
        final LoggingStyleSheetCounter counter = new LoggingStyleSheetCounter(internalCounter, logger);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final int count = 7;
        Mockito.when(internalCounter.count(stylesheet)).thenReturn(count);

        Assert.assertEquals("Should return 7.", count, counter.count(stylesheet));
        Mockito.verify(internalCounter).count(stylesheet);
        Mockito.verify(logger).info("Stylesheet contains 7 rules.");
        Mockito.verifyNoMoreInteractions(internalCounter, logger);
    }
}
