package biz.gabrys.maven.plugins.css.splitter.message;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractMessagePrinterTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(null);
        Assert.assertFalse("Should return false for null", supported);
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new NotSupportedTestNodeRule());
        Assert.assertFalse("Should return false for invalid rule", supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final MessagePrinterImpl printer = new MessagePrinterImpl();
        final boolean supported = printer.isSupportedType(new SupportedTestNodeRule());
        Assert.assertTrue("Should return false for valid rule", supported);
    }

    @Test
    public void count_ruleIsInvalid_doesNothing() {
        final MessagePrinterImpl printer = Mockito.spy(new MessagePrinterImpl());

        final NodeRule rule = new NotSupportedTestNodeRule();
        printer.print(rule);

        Mockito.verify(printer, Mockito.never()).print2(Matchers.any(SupportedTestNodeRule.class));
    }

    @Test
    public void convert_typeIsValid_executesPrint2() {
        final MessagePrinterImpl printer = Mockito.spy(new MessagePrinterImpl());

        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        printer.print(rule);

        Mockito.verify(printer).print2(rule);
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
