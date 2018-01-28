package biz.gabrys.maven.plugins.css.splitter.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRuleSizeMessagePrinterTest {

    @Test
    public void isEnabled_debugIsEnabled_returnsTrue() {
        final Log logger = mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);
        when(logger.isDebugEnabled()).thenReturn(Boolean.TRUE);

        final boolean enabled = printer.isEnabled();

        assertThat(enabled).isTrue();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void isEnabled_debugIsDisabled_returnsFalse() {
        final Log logger = mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);
        when(logger.isDebugEnabled()).thenReturn(Boolean.FALSE);

        final boolean enabled = printer.isEnabled();

        assertThat(enabled).isFalse();
        verify(logger).isDebugEnabled();
        verifyNoMoreInteractions(logger);
    }

    @Test
    public void print2_ruleWithOneProperty_prints() {
        final Log logger = mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = mock(UnknownRule.class);
        when(rule.getCode()).thenReturn("_code_");
        when(rule.getSize()).thenReturn(1);

        printer.print2(rule);

        verify(rule).getCode();
        verify(logger).debug("Found non-standard (unknown) rule:\n_code_");
        verify(rule).getSize();
        verify(logger).debug("I treat that it contains 1 property.");
        verifyNoMoreInteractions(logger, rule);
    }

    @Test
    public void print2_ruleWithZeroProperties_prints() {
        final Log logger = mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = mock(UnknownRule.class);
        when(rule.getCode()).thenReturn("@f {}");
        when(rule.getSize()).thenReturn(0);

        printer.print2(rule);

        verify(rule).getCode();
        verify(logger).debug("Found non-standard (unknown) rule:\n@f {}");
        verify(rule).getSize();
        verify(logger).debug("I treat that it contains 0 properties.");
        verifyNoMoreInteractions(logger, rule);
    }

    @Test
    public void print2_ruleWithThreeProperties_prints() {
        final Log logger = mock(Log.class);
        final UnknownRuleSizeMessagePrinter printer = new UnknownRuleSizeMessagePrinter(logger);

        final UnknownRule rule = mock(UnknownRule.class);
        when(rule.getCode()).thenReturn("_three_");
        when(rule.getSize()).thenReturn(3);

        printer.print2(rule);

        verify(rule).getCode();
        verify(logger).debug("Found non-standard (unknown) rule:\n_three_");
        verify(rule).getSize();
        verify(logger).debug("I treat that it contains 3 properties.");
        verifyNoMoreInteractions(logger, rule);
    }
}
