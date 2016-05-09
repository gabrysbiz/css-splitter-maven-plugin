package biz.gabrys.maven.plugins.css.splitter.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class ComplexRuleInvalidContentMessagePrinterTest {

    @Test
    public void isEnabled_strictIsEnabled_returnsFalse() {
        final Log logger = Mockito.mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, true);
        Assert.assertFalse("Should return false in strict mode.", printer.isEnabled());
    }

    @Test
    public void isEnabled_strictIsDisabled_returnsTrue() {
        final Log logger = Mockito.mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);
        Assert.assertTrue("Should return true in non-strict mode.", printer.isEnabled());
    }

    @Test
    public void print2_allRulesAreCorrect_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        rules.add(Mockito.mock(StyleRule.class));
        rules.add(Mockito.mock(StyleRule.class));
        rules.add(Mockito.mock(StyleRule.class));

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(rule.getRules()).thenReturn(rules);

        printer.print2(rule);
        Mockito.verifyZeroInteractions(logger);
    }

    @Test
    public void print2_containInvalidRules_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final ComplexRuleInvalidContentMessagePrinter printer = new ComplexRuleInvalidContentMessagePrinter(logger, false);

        final List<NodeRule> rules = new ArrayList<NodeRule>();
        final UnknownRule rule1 = Mockito.mock(UnknownRule.class);
        Mockito.when(rule1.getCode()).thenReturn("unknown-code");
        rules.add(rule1);
        rules.add(Mockito.mock(StyleRule.class));
        final ComplexRule rule2 = Mockito.mock(ComplexRule.class);
        Mockito.when(rule2.getCode()).thenReturn("complex-code");
        rules.add(rule2);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(rule.getType()).thenReturn("@media");
        Mockito.when(rule.getCode()).thenReturn("full-code");
        Mockito.when(rule.getRules()).thenReturn(rules);

        printer.print2(rule);
        Mockito.verify(logger, Mockito.times(2)).warn("NON-STRICT: found non-standard content in @media rule!");
        Mockito.verify(logger).warn(String.format("NON-STRICT: non-standard content:%nunknown-code"));
        Mockito.verify(logger, Mockito.times(2)).warn(String.format("NON-STRICT: @media rule code:%nfull-code"));
        Mockito.verify(logger).warn(String.format("NON-STRICT: non-standard content:%ncomplex-code"));
        Mockito.verifyZeroInteractions(logger);
    }
}
