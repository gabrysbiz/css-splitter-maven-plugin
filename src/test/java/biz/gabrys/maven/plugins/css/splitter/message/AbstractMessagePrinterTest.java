package biz.gabrys.maven.plugins.css.splitter.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractMessagePrinterTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(null);
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new NotSupportedTestNodeRule());
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new SupportedTestNodeRule());
        assertThat(supported).isTrue();
    }

    @Test
    public void count_ruleIsInvalid_doesNothing() {
        final MessagePrinterImpl printer = spy(new MessagePrinterImpl());

        final NodeRule rule = new NotSupportedTestNodeRule();
        printer.print(rule);

        verify(printer, never()).print2(any(SupportedTestNodeRule.class));
    }

    @Test
    public void convert_typeIsValid_executesPrint2() {
        final MessagePrinterImpl printer = spy(new MessagePrinterImpl());

        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        printer.print(rule);

        verify(printer).print2(rule);
    }

    private static class MessagePrinterImpl extends AbstractMessagePrinter<SupportedTestNodeRule> {

        MessagePrinterImpl() {
            super(SupportedTestNodeRule.class);
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        protected void print2(final SupportedTestNodeRule rule) {
            // do nothing
        }
    }
}
