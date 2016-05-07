package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractRulePropertiesLimitValidatorTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        Assert.assertFalse("Should return false for null rule.", validator.isSupportedType(null));
    }

    @Test
    public void isSupportedType_ruleClassIsNotSupported_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        Assert.assertFalse("Should return false for not supported rule.", validator.isSupportedType(new NotSupportedTestNodeRule()));
    }

    @Test
    public void isSupportedType_ruleClassIsSupported_returnsTrue() {
        final TestValidator<SupportedTestNodeRule> validator = new TestValidator<SupportedTestNodeRule>(SupportedTestNodeRule.class);
        Assert.assertTrue("Should return true for supported rule.", validator.isSupportedType(new SupportedTestNodeRule()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_ruleClassIsNotSupported_throwsException() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        validator.validate(new SimpleRule("code"), 0);
    }

    @Test
    public void validate_ruleClassIsSupported_executesValidate2() {
        final TestValidator<SupportedTestNodeRule> validator = Mockito
                .spy(new TestValidator<SupportedTestNodeRule>(SupportedTestNodeRule.class));

        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        final int limit = 10;
        validator.validate(rule, limit);

        Mockito.verify(validator).validate2(rule, limit);
    }

    private static class TestValidator<T extends NodeRule> extends AbstractRulePropertiesLimitValidator<T> {

        TestValidator(final Class<T> clazz) {
            super(clazz);
        }

        @Override
        protected void validate2(final T rule, final int limit) {
            // do nothing
        }
    }
}
