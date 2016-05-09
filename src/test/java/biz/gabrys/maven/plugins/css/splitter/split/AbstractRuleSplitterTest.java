package biz.gabrys.maven.plugins.css.splitter.split;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractRuleSplitterTest {

    @Test
    public void isSplittable_ruleHasInvalidType_returnsFalse() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        final boolean splittable = splitter.isSplittable(new NotSupportedTestNodeRule());
        Assert.assertFalse("Should return false for not supported rule", splittable);
    }

    @Test
    public void isSplittable_ruleHasValidType_executesIsSplittable2() {
        final RuleSplitterImpl splitter = Mockito.spy(new RuleSplitterImpl());
        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        splitter.isSplittable(rule);
        Mockito.verify(splitter).isSplittable2(rule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_typeIsInvalid_throwsException() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        splitter.split(new NotSupportedTestNodeRule(), 2);
    }

    @Test
    public void split_typeIsValid_executesConvert2() {
        final RuleSplitterImpl splitter = Mockito.spy(new RuleSplitterImpl());
        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        final int splitAfter = 3;
        splitter.split(rule, splitAfter);
        Mockito.verify(splitter).split2(rule, splitAfter);
    }

    private static class RuleSplitterImpl extends AbstractRuleSplitter<SupportedTestNodeRule> {

        RuleSplitterImpl() {
            super(SupportedTestNodeRule.class);
        }

        @Override
        protected boolean isSplittable2(final SupportedTestNodeRule rule) {
            // do nothing
            return true;
        }

        @Override
        protected SplitResult split2(final SupportedTestNodeRule rule, final int splitAfter) {
            // do nothing
            return null;
        }
    }
}
