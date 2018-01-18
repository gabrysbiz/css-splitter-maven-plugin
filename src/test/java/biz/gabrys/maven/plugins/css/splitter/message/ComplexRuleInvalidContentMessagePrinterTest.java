package biz.gabrys.maven.plugins.css.splitter.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class ComplexRuleInvalidContentMessagePrinterTest {

    @Test
    public void isEnabled_strictIsEnabled_returnsFalse() {
        final Log logger = mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, true);

        final boolean enabled = printer.isEnabled();

        assertThat(enabled).isFalse();
    }

    @Test
    public void isEnabled_strictIsDisabled_returnsTrue() {
        final Log logger = mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);

        final boolean enabled = printer.isEnabled();

        assertThat(enabled).isTrue();
    }

    @Test
    public void print2_allRulesAreCorrect_doesNothing() {
        final Log logger = mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        rules.add(mock(StyleRule.class));
        rules.add(mock(StyleRule.class));
        rules.add(mock(StyleRule.class));

        final ComplexRule rule = mock(ComplexRule.class);
        when(rule.getRules()).thenReturn(rules);

        printer.print2(rule);

        verifyZeroInteractions(logger);
    }

    @Test
    public void print2_containInvalidRules_doesNothing() {
        final Log logger = mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final UnknownRule rule1 = mock(UnknownRule.class);
        when(rule1.getCode()).thenReturn("unknown-code");
        rules.add(rule1);
        rules.add(mock(StyleRule.class));
        final ComplexRule rule2 = mock(ComplexRule.class);
        when(rule2.getCode()).thenReturn("complex-code");
        rules.add(rule2);

        final ComplexRule rule = mock(ComplexRule.class);
        when(rule.getType()).thenReturn("@media");
        when(rule.getCode()).thenReturn("full-code");
        when(rule.getRules()).thenReturn(rules);

        printer.print2(rule);

        verify(logger, times(2)).warn("NON-STRICT: found non-standard content in @media rule!");
        verify(logger).warn(String.format("NON-STRICT: non-standard content:%nunknown-code"));
        verify(logger, times(2)).warn(String.format("NON-STRICT: @media rule code:%nfull-code"));
        verify(logger).warn(String.format("NON-STRICT: non-standard content:%ncomplex-code"));
        verifyZeroInteractions(logger);
    }
}
