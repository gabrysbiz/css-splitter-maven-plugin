package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.SimpleRule;
import biz.gabrys.maven.plugins.css.splitter.test.NotSupportedTestNodeRule;
import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class AbstractRulePropertiesLimitValidatorTest {

    @Test
    public void isSupportedType_ruleIsNull_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        final boolean supported = validator.isSupportedType(null);
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleClassIsNotSupported_returnsFalse() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        final boolean supported = validator.isSupportedType(new NotSupportedTestNodeRule());
        assertThat(supported).isFalse();
    }

    @Test
    public void isSupportedType_ruleClassIsSupported_returnsTrue() {
        final TestValidator<SupportedTestNodeRule> validator = new TestValidator<SupportedTestNodeRule>(SupportedTestNodeRule.class);
        final boolean supported = validator.isSupportedType(new SupportedTestNodeRule());
        assertThat(supported).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_ruleClassIsNotSupported_throwsException() {
        final TestValidator<NodeRule> validator = new TestValidator<NodeRule>(NodeRule.class);
        validator.validate(new SimpleRule("code"), 0);
    }

    @Test
    public void validate_ruleClassIsSupported_executesValidate2() {
        final TestValidator<SupportedTestNodeRule> validator = spy(new TestValidator<SupportedTestNodeRule>(SupportedTestNodeRule.class));

        final SupportedTestNodeRule rule = new SupportedTestNodeRule();
        final int limit = 10;
        validator.validate(rule, limit);

        verify(validator).validate2(rule, limit);
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
