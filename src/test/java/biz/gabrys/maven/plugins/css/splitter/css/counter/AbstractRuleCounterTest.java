package biz.gabrys.maven.plugins.css.splitter.css.counter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

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
        final boolean supported = counter.isSupportedType(new NotSupportedTestNodeRule());
        Assert.assertFalse("Should return false for invalid rule", supported);
    }

    @Test
    public void isSupportedType_ruleHasValidType_returnsTrue() {
        final RuleCounterImpl counter = new RuleCounterImpl();
        final boolean supported = counter.isSupportedType(new SupportedTestNodeRule());
        Assert.assertTrue("Should return false for valid rule", supported);
    }

    @Test(expected = UnsupportedRuleException.class)
    public void count_ruleIsInvalid_throwsException() {
        final RuleCounterImpl counter = Mockito.spy(new RuleCounterImpl());
        counter.count(new NotSupportedTestNodeRule());
    }

    @Test
    public void convert_typeIsValid_executesCount2() {
        final RuleCounterImpl counter = Mockito.spy(new RuleCounterImpl());

        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        counter.count(rule);

        Mockito.verify(counter).count2(rule);
    }

    private static class RuleCounterImpl extends AbstractRuleCounter<SupportedTestNodeRule> {

        RuleCounterImpl() {
            super(SupportedTestNodeRule.class);
        }

        @Override
        protected int count2(final SupportedTestNodeRule rule) {
            return 0;
        }
    }
}
