package biz.gabrys.maven.plugins.css.splitter.counter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class AbstractRuleCounterTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final RuleCounterImpl counter = new RuleCounterImpl();
        final boolean supported = counter.isSupportedType(null);
        Assert.assertFalse("Should return false for null", supported);
    }

    @Test
    public void isSupportedType_ruleHasInvalidType_returnsFalse() {
        final RuleCounterImpl counter = new RuleCounterImpl();
        final boolean supported = counter.isSupportedType(new NotSupportedNodeRule());
        Assert.assertFalse("Should return false for invalid rule", supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsFalse() {
        final RuleCounterImpl counter = new RuleCounterImpl();
        final boolean supported = counter.isSupportedType(new SupportedNodeRule());
        Assert.assertTrue("Should return false for valid rule", supported);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void count_ruleIsInvalid_throwsException() {
        final RuleCounterImpl counter = Mockito.spy(new RuleCounterImpl());
        counter.count(new NotSupportedNodeRule());
    }

    @Test
    public void convert_typeIsValid_executesCount2() {
        final RuleCounterImpl counter = Mockito.spy(new RuleCounterImpl());
        final SupportedNodeRule rule = new SupportedNodeRule();
        counter.count(rule);
        Mockito.verify(counter).count2(rule);
    }

    private static class RuleCounterImpl extends AbstractRuleCounter<SupportedNodeRule> {

        RuleCounterImpl() {
            super(SupportedNodeRule.class);
        }

        @Override
        protected int count2(final SupportedNodeRule rule) {
            return 0;
        }
    }

    private static class SupportedNodeRule extends NodeRule {

    }

    private static class NotSupportedNodeRule extends NodeRule {

    }
}
