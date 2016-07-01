package biz.gabrys.maven.plugins.css.splitter.message;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class TreeMessagePrinterTest {

    @Test
    public void isEnabled_internalPrinterIsEnabled_returnsTrue() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        Mockito.when(internalPrinter.isEnabled()).thenReturn(Boolean.TRUE);
        final boolean enabled = printer.isEnabled();

        Assert.assertTrue("Enabled value.", enabled);
    }

    @Test
    public void isEnabled_internalPrinterIsDisabled_returnsFalse() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        Mockito.when(internalPrinter.isEnabled()).thenReturn(Boolean.FALSE);
        final boolean enabled = printer.isEnabled();

        Assert.assertFalse("Enabled value.", enabled);
    }

    @Test
    public void print_nonComplexRuleAndInternalPrinterSupportsIt() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.TRUE);
        printer.print(rule);

        Mockito.verify(internalPrinter).isSupportedType(rule);
        Mockito.verify(internalPrinter).print(rule);
        Mockito.verifyNoMoreInteractions(internalPrinter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test
    public void print_nonComplexRuleAndInternalPrinterDoesNotSupportIt() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.FALSE);
        printer.print(rule);

        Mockito.verify(internalPrinter).isSupportedType(rule);
        Mockito.verifyNoMoreInteractions(internalPrinter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test
    public void print_complexRuleAndInternalPrinterSupportsIt() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.TRUE);

        final NodeRule child1 = Mockito.mock(NodeRule.class);
        Mockito.when(internalPrinter.isSupportedType(child1)).thenReturn(Boolean.FALSE);

        final ComplexRule child2 = Mockito.mock(ComplexRule.class);
        Mockito.when(internalPrinter.isSupportedType(child2)).thenReturn(Boolean.TRUE);
        Mockito.when(child2.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(child1, child2));

        printer.print(rule);

        Mockito.verify(internalPrinter).isSupportedType(rule);
        Mockito.verify(internalPrinter).print(rule);
        Mockito.verify(rule).getRules();
        Mockito.verify(internalPrinter).isSupportedType(child1);
        Mockito.verify(internalPrinter).isSupportedType(child2);
        Mockito.verify(internalPrinter).print(child2);
        Mockito.verify(child2).getRules();
        Mockito.verifyNoMoreInteractions(internalPrinter, rule, child2);
        Mockito.verifyZeroInteractions(child1);
    }

    @Test
    public void print_complexRuleAndInternalPrinterDoesNotSupportIt() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final TreeMessagePrinter printer = new TreeMessagePrinter(internalPrinter);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(internalPrinter.isSupportedType(rule)).thenReturn(Boolean.FALSE);

        final NodeRule child1 = Mockito.mock(NodeRule.class);
        Mockito.when(internalPrinter.isSupportedType(child1)).thenReturn(Boolean.FALSE);

        final ComplexRule child2 = Mockito.mock(ComplexRule.class);
        Mockito.when(internalPrinter.isSupportedType(child2)).thenReturn(Boolean.TRUE);
        Mockito.when(child2.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(child1, child2));

        printer.print(rule);

        Mockito.verify(internalPrinter).isSupportedType(rule);
        Mockito.verify(rule).getRules();
        Mockito.verify(internalPrinter).isSupportedType(child1);
        Mockito.verify(internalPrinter).isSupportedType(child2);
        Mockito.verify(internalPrinter).print(child2);
        Mockito.verify(child2).getRules();
        Mockito.verifyNoMoreInteractions(internalPrinter, rule, child2);
        Mockito.verifyZeroInteractions(child1);
    }
}
