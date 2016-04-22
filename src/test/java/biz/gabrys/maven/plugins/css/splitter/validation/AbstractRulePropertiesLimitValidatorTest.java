package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.SimpleRule;

public final class AbstractRulePropertiesLimitValidatorTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        Assert.assertFalse("Should return false for null rule.", validator.isSupportedType(null));
    }

    @Test
    public void isSupportedType_ruleClassIsNotSupported_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        Assert.assertFalse("Should return false for not supported rule.", validator.isSupportedType(new NodeRuleImpl()));
    }

    @Test
    public void isSupportedType_ruleClassIsSupported_returnsTrue() {
        final TestValidator<NodeRuleImpl> validator = new TestValidator<NodeRuleImpl>(NodeRuleImpl.class);
        Assert.assertTrue("Should return true for supported rule.", validator.isSupportedType(new NodeRuleImpl()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_ruleClassIsNotSupported_throwsException() throws ValidationException {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        validator.validate(new SimpleRule("code"), 0);
    }

    @Test
    public void validate_ruleClassIsSupported_executesValidate2() throws ValidationException {
        final TestValidator<NodeRuleImpl> validator = Mockito.spy(new TestValidator<NodeRuleImpl>(NodeRuleImpl.class));

        final NodeRuleImpl rule = new NodeRuleImpl();
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

    private static class NodeRuleImpl extends NodeRule {

    }
}
