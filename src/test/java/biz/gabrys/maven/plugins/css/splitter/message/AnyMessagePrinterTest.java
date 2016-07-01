package biz.gabrys.maven.plugins.css.splitter.message;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class AnyMessagePrinterTest {

    @Test
    public void isEnabled_printersAreEmpty_returnsFalse() {
        final AnyMessagePrinter printer = new AnyMessagePrinter(Collections.<MessagePrinter>emptyList());
        Assert.assertFalse("Printer should be disabled.", printer.isEnabled());
    }

    @Test
    public void isEnabled_printersAreNotEmpty_returnsTrue() {
        final List<MessagePrinter> printers = Arrays.asList(Mockito.mock(MessagePrinter.class));
        final AnyMessagePrinter printer = new AnyMessagePrinter(printers);
        Assert.assertTrue("Printer should be enabled.", printer.isEnabled());
    }

    @Test
    public void print() {
        final MessagePrinter internalPrinter1 = Mockito.mock(MessagePrinter.class);
        final MessagePrinter internalPrinter2 = Mockito.mock(MessagePrinter.class);
        final MessagePrinter internalPrinter3 = Mockito.mock(MessagePrinter.class);
        final List<MessagePrinter> printers = Arrays.asList(internalPrinter1, internalPrinter2, internalPrinter3);
        final AnyMessagePrinter printer = new AnyMessagePrinter(printers);

        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(internalPrinter1.isSupportedType(rule)).thenReturn(Boolean.TRUE);
        Mockito.when(internalPrinter2.isSupportedType(rule)).thenReturn(Boolean.FALSE);
        Mockito.when(internalPrinter3.isSupportedType(rule)).thenReturn(Boolean.TRUE);

        printer.print(rule);

        Mockito.verify(internalPrinter1).isSupportedType(rule);
        Mockito.verify(internalPrinter1).print(rule);
        Mockito.verify(internalPrinter2).isSupportedType(rule);
        Mockito.verify(internalPrinter3).isSupportedType(rule);
        Mockito.verify(internalPrinter3).print(rule);
        Mockito.verifyNoMoreInteractions(internalPrinter1, internalPrinter2, internalPrinter3);
    }
}
