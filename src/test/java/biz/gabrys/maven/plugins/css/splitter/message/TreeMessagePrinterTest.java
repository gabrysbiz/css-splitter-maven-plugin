package biz.gabrys.maven.plugins.css.splitter.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class TreeMessagePrinterTest {

    @Test
    public void isEnabled_internalPrinterIsEnabled_returnsTrue() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        when(internalPrinter.isEnabled()).thenReturn(Boolean.TRUE);
        final boolean enabled = printer.isEnabled();

        assertTrue(enabled);
    }

    @Test
    public void isEnabled_internalPrinterIsDisabled_returnsFalse() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        when(internalPrinter.isEnabled()).thenReturn(Boolean.FALSE);
        final boolean enabled = printer.isEnabled();

        assertFalse(enabled);
    }

    @Test
    public void print_nonComplexRuleAndInternalPrinterSupportsIt() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final NodeRule rule = mock(NodeRule.class);
        when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.TRUE);
        printer.print(rule);

        verify(internalPrinter).isSupportedType(rule);
        verify(internalPrinter).print(rule);
        verifyNoMoreInteractions(internalPrinter);
        verifyZeroInteractions(rule);
    }

    @Test
    public void print_nonComplexRuleAndInternalPrinterDoesNotSupportIt() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final NodeRule rule = mock(NodeRule.class);
        when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.FALSE);
        printer.print(rule);

        verify(internalPrinter).isSupportedType(rule);
        verifyNoMoreInteractions(internalPrinter);
        verifyZeroInteractions(rule);
    }

    @Test
    public void print_complexRuleAndInternalPrinterSupportsIt() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final ComplexRule rule = mock(ComplexRule.class);
        when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.TRUE);

        final NodeRule child1 = mock(NodeRule.class);
        when(internalPrinter.isSupportedType(child1)).thenReturn(Boolean.FALSE);

        final ComplexRule child2 = mock(ComplexRule.class);
        when(internalPrinter.isSupportedType(child2)).thenReturn(Boolean.TRUE);
        when(child2.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        when(rule.getRules()).thenReturn(Arrays.asList(child1, child2));

        printer.print(rule);

        verify(internalPrinter).isSupportedType(rule);
        verify(internalPrinter).print(rule);
        verify(rule).getRules();
        verify(internalPrinter).isSupportedType(child1);
        verify(internalPrinter).isSupportedType(child2);
        verify(internalPrinter).print(child2);
        verify(child2).getRules();
        verifyNoMoreInteractions(internalPrinter, rule, child2);
        verifyZeroInteractions(child1);
    }

    @Test
    public void print_complexRuleAndInternalPrinterDoesNotSupportIt() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final ComplexRule rule = mock(ComplexRule.class);
        when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.FALSE);

        final NodeRule child1 = mock(NodeRule.class);
        when(internalPrinter.isSupportedType(child1)).thenReturn(Boolean.FALSE);

        final ComplexRule child2 = mock(ComplexRule.class);
        when(internalPrinter.isSupportedType(child2)).thenReturn(Boolean.TRUE);
        when(child2.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        when(rule.getRules()).thenReturn(Arrays.asList(child1, child2));

        printer.print(rule);

        verify(internalPrinter).isSupportedType(rule);
        verify(rule).getRules();
        verify(internalPrinter).isSupportedType(child1);
        verify(internalPrinter).isSupportedType(child2);
        verify(internalPrinter).print(child2);
        verify(child2).getRules();
        verifyNoMoreInteractions(internalPrinter, rule, child2);
        verifyZeroInteractions(child1);
    }
}
