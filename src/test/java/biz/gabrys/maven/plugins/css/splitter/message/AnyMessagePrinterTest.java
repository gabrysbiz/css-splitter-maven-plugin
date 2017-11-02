package biz.gabrys.maven.plugins.css.splitter.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class AnyMessagePrinterTest {

    @Test
    public void isEnabled_printersAreEmpty_returnsFalse() {
        final AnyMessagePrinter printer = new AnyMessagePrinter(Collections.<MessagePrinter>emptyList());
        assertFalse(printer.isEnabled());
    }

    @Test
    public void isEnabled_printersAreNotEmpty_returnsTrue() {
        final List<MessagePrinter> printers = Arrays.asList(mock(MessagePrinter.class));
        final AnyMessagePrinter printer = new AnyMessagePrinter(printers);
        assertTrue(printer.isEnabled());
    }

    @Test
    public void print() {
        final MessagePrinter internalPrinter1 = mock(MessagePrinter.class);
        final MessagePrinter internalPrinter2 = mock(MessagePrinter.class);
        final MessagePrinter internalPrinter3 = mock(MessagePrinter.class);
        final List<MessagePrinter> printers = Arrays.asList(internalPrinter1, internalPrinter2, internalPrinter3);
        final AnyMessagePrinter printer = new AnyMessagePrinter(printers);

        final NodeRule rule = mock(NodeRule.class);
        when(internalPrinter1.isSupportedType(rule)).thenReturn(Boolean.TRUE);
        when(internalPrinter2.isSupportedType(rule)).thenReturn(Boolean.FALSE);
        when(internalPrinter3.isSupportedType(rule)).thenReturn(Boolean.TRUE);

        printer.print(rule);

        verify(internalPrinter1).isSupportedType(rule);
        verify(internalPrinter1).print(rule);
        verify(internalPrinter2).isSupportedType(rule);
        verify(internalPrinter3).isSupportedType(rule);
        verify(internalPrinter3).print(rule);
        verifyNoMoreInteractions(internalPrinter1, internalPrinter2, internalPrinter3);
    }
}
