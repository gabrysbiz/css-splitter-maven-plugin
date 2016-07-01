package biz.gabrys.maven.plugins.css.splitter.message;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRuleSizeMessagePrinterTest {

    @Test
    public void isEnabled_debugIsEnabled_returnsTrue() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        Assert.assertTrue("Printer should be enabled.", printer.isEnabled());
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    public void isEnabled_debugIsDisabled_returnsFalse() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        Assert.assertFalse("Printer should be disabled.", printer.isEnabled());
        Mockito.verify(logger).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    public void print2_ruleWithOneProperty() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("_code_");
        Mockito.when(rule.getSize()).thenReturn(1);

        printer.print2(rule);
        Mockito.verify(rule).getCode();
        Mockito.verify(logger).debug("Found non-standard (unknown) rule:\n_code_");
        Mockito.verify(rule).getSize();
        Mockito.verify(logger).debug("I treat that it contains 1 property.");
        Mockito.verifyNoMoreInteractions(logger, rule);
    }

    @Test
    public void print2_ruleWithZeroProperties() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("@f {}");
        Mockito.when(rule.getSize()).thenReturn(0);

        printer.print2(rule);
        Mockito.verify(rule).getCode();
        Mockito.verify(logger).debug("Found non-standard (unknown) rule:\n@f {}");
        Mockito.verify(rule).getSize();
        Mockito.verify(logger).debug("I treat that it contains 0 properties.");
        Mockito.verifyNoMoreInteractions(logger, rule);
    }

    @Test
    public void print2_ruleWithThreeProperties() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("_three_");
        Mockito.when(rule.getSize()).thenReturn(3);

        printer.print2(rule);
        Mockito.verify(rule).getCode();
        Mockito.verify(logger).debug("Found non-standard (unknown) rule:\n_three_");
        Mockito.verify(rule).getSize();
        Mockito.verify(logger).debug("I treat that it contains 3 properties.");
        Mockito.verifyNoMoreInteractions(logger, rule);
    }
}
