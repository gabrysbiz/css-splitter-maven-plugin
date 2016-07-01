package biz.gabrys.maven.plugins.css.splitter.message;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StylesheetMessagePrinterTest {

    @Test
    public void print_internalPrinterIsEnabled_prints() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final StylesheetMessagePrinter printer = new StylesheetMessagePrinter(internalPrinter);

        Mockito.when(internalPrinter.isEnabled()).thenReturn(Boolean.TRUE);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        final NodeRule rule3 = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2, rule3));

        printer.print(stylesheet);

        Mockito.verify(internalPrinter).isEnabled();
        Mockito.verify(stylesheet).getRules();
        Mockito.verify(internalPrinter).print(rule1);
        Mockito.verify(internalPrinter).print(rule2);
        Mockito.verify(internalPrinter).print(rule3);
        Mockito.verifyNoMoreInteractions(internalPrinter, stylesheet);
        Mockito.verifyZeroInteractions(rule1, rule2, rule3);
    }

    @Test
    public void print_internalPrinterIsDisabled_doesNothing() {
        final MessagePrinter internalPrinter = Mockito.mock(MessagePrinter.class);
        final StylesheetMessagePrinter printer = new StylesheetMessagePrinter(internalPrinter);

        Mockito.when(internalPrinter.isEnabled()).thenReturn(Boolean.FALSE);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        final NodeRule rule3 = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2, rule3));

        printer.print(stylesheet);

        Mockito.verify(internalPrinter).isEnabled();
        Mockito.verifyNoMoreInteractions(internalPrinter);
        Mockito.verifyZeroInteractions(stylesheet, rule1, rule2, rule3);
    }
}
