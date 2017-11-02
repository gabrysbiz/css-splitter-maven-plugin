package biz.gabrys.maven.plugins.css.splitter.split;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractRuleSplitterTest {

    @Test
    public void isSplittable_ruleHasInvalidType_returnsFalse() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        final boolean splittable = splitter.isSplittable(new NotSupportedTestNodeRule());
        assertFalse(splittable);
    }

    @Test
    public void isSplittable_ruleHasValidType_executesIsSplittable2() {
        final RuleSplitterImpl splitter = spy(new RuleSplitterImpl());
        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        splitter.isSplittable(rule);
        verify(splitter).isSplittable2(rule);
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_typeIsInvalid_throwsException() {
        final RuleSplitterImpl splitter = new RuleSplitterImpl();
        splitter.split(new NotSupportedTestNodeRule(), 2);
    }

    @Test
    public void split_typeIsValid_executesConvert2() {
        final RuleSplitterImpl splitter = spy(new RuleSplitterImpl());
        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        final int splitAfter = 3;
        splitter.split(rule, splitAfter);
        verify(splitter).split2(rule, splitAfter);
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
