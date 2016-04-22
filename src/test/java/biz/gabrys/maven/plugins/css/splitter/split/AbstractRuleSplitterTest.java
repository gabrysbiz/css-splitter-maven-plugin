package biz.gabrys.maven.plugins.css.splitter.split;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public final class AbstractRuleSplitterTest {

    @Test
    public void isSplittable_ruleHasInvalidType_returnsFalse() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        final boolean splittable = splitter.isSplittable(new NotSupportedRule());
        Assert.assertFalse("Should return false for not supported rule", splittable);
    }

    @Test
    public void isSplittable_ruleHasValidType_executesIsSplittable2() {
        final RuleSplitterImpl splitter = Mockito.spy(new RuleSplitterImpl());
        final SupportedRule rule = new SupportedRule();
        splitter.isSplittable(rule);
        Mockito.verify(splitter).isSplittable2(rule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_typeIsInvalid_throwsException() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        splitter.split(new NotSupportedRule(), 2);
    }

    @Test
    public void split_typeIsValid_executesConvert2() {
        final RuleSplitterImpl splitter = Mockito.spy(new RuleSplitterImpl());
        final SupportedRule rule = new SupportedRule();
        final int splitAfter = 3;
        splitter.split(rule, splitAfter);
        Mockito.verify(splitter).split2(rule, splitAfter);
    }

    private static class RuleSplitterImpl extends AbstractRuleSplitter<SupportedRule> {

        RuleSplitterImpl() {
            super(SupportedRule.class);
        }

        @Override
        protected boolean isSplittable2(final SupportedRule rule) {
            // do nothing
            return true;
        }

        @Override
        protected SplitResult<SupportedRule> split2(final SupportedRule rule, final int splitAfter) {
            // do nothing
            return null;
        }
    }

    private static class SupportedRule extends NodeRule {

    }

    private static class NotSupportedRule extends NodeRule {

    }
}
