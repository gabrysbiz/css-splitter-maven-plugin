package biz.gabrys.maven.plugins.css.splitter.message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StylesheetMessagePrinterTest {

    @Test
    public void print_internalPrinterIsEnabled_prints() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final StylesheetMessagePrinter printer = new StylesheetMessagePrinter(internalPrinter);

        when(internalPrinter.isEnabled()).thenReturn(Boolean.TRUE);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule1 = mock(NodeRule.class);
        final NodeRule rule2 = mock(NodeRule.class);
        final NodeRule rule3 = mock(NodeRule.class);
        when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2, rule3));

        printer.print(stylesheet);

        verify(internalPrinter).isEnabled();
        verify(stylesheet).getRules();
        verify(internalPrinter).print(rule1);
        verify(internalPrinter).print(rule2);
        verify(internalPrinter).print(rule3);
        verifyNoMoreInteractions(internalPrinter, stylesheet);
        verifyZeroInteractions(rule1, rule2, rule3);
    }

    @Test
    public void print_internalPrinterIsDisabled_doesNothing() {
        final MessagePrinter internalPrinter = mock(MessagePrinter.class);
        final StylesheetMessagePrinter printer = new StylesheetMessagePrinter(internalPrinter);

        when(internalPrinter.isEnabled()).thenReturn(Boolean.FALSE);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule1 = mock(NodeRule.class);
        final NodeRule rule2 = mock(NodeRule.class);
        final NodeRule rule3 = mock(NodeRule.class);
        when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2, rule3));

        printer.print(stylesheet);

        verify(internalPrinter).isEnabled();
        verifyNoMoreInteractions(internalPrinter);
        verifyZeroInteractions(stylesheet, rule1, rule2, rule3);
    }
}
