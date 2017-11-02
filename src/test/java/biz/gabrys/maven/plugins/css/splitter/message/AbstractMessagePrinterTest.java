package biz.gabrys.maven.plugins.css.splitter.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
        assertFalse(supported);
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new NotSupportedTestNodeRule());
        assertFalse(supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new SupportedTestNodeRule());
        assertTrue(supported);
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

        public boolean isEnabled() {
            return false;
        }

        @Override
        protected void print2(final SupportedTestNodeRule rule) {
            // do nothing
        }
    }
}
