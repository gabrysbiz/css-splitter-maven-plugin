package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class ComplexRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleDoesNotContainChildren_doesNothing() {
        final RulePropertiesLimitValidator childRulesValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(childRulesValidator);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(rule.getRules()).thenReturn(Collections.<StyleRule>emptyList());

        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).getRules();
        Mockito.verifyZeroInteractions(rule, childRulesValidator);
    }

    @Test
    public void validate2_ruleContainsValidChildren_doesNothing() {
        final RulePropertiesLimitValidator childRulesValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(childRulesValidator);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        final StyleRule childRule1 = Mockito.mock(StyleRule.class);
        final StyleRule childRule2 = Mockito.mock(StyleRule.class);
        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).getRules();
        Mockito.verify(childRulesValidator).validate(childRule1, limit);
        Mockito.verify(childRulesValidator).validate(childRule2, limit);
        Mockito.verifyNoMoreInteractions(rule, childRulesValidator);
        Mockito.verifyZeroInteractions(childRule1, childRule2);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleContainsInvalidChildren_throwsException() {
        final RulePropertiesLimitValidator childRulesValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(childRulesValidator);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        final StyleRule childRule1 = Mockito.mock(StyleRule.class);
        final StyleRule childRule2 = Mockito.mock(StyleRule.class);
        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        final int limit = 10;
        Mockito.doThrow(new ValidationException("error")).when(childRulesValidator).validate(childRule2, limit);

        validator.validate2(rule, limit);
    }
}
