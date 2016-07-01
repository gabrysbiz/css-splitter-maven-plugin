package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class ComplexRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleDoesNotContainChildren_doesNothing() {
        final List<RulePropertiesLimitValidator> validators = new ArrayList<RulePropertiesLimitValidator>();
        final RulePropertiesLimitValidator internalValidator1 = Mockito.mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator1);
        final RulePropertiesLimitValidator internalValidator2 = Mockito.mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator2);

        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(validators);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        Mockito.when(rule.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).getRules();
        Mockito.verifyZeroInteractions(rule, internalValidator1, internalValidator2);
    }

    @Test
    public void validate2_ruleContainsValidChildren_doesNothing() {
        final List<RulePropertiesLimitValidator> validators = new ArrayList<RulePropertiesLimitValidator>();
        final RulePropertiesLimitValidator internalValidator1 = Mockito.mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator1);
        final RulePropertiesLimitValidator internalValidator2 = Mockito.mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator2);

        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(validators);

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        final NodeRule childRule1 = Mockito.mock(NodeRule.class);
        final NodeRule childRule2 = Mockito.mock(NodeRule.class);
        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        Mockito.when(internalValidator1.isSupportedType(childRule1)).thenReturn(Boolean.TRUE);
        Mockito.when(internalValidator2.isSupportedType(childRule1)).thenReturn(Boolean.FALSE);
        Mockito.when(internalValidator1.isSupportedType(childRule2)).thenReturn(Boolean.FALSE);
        Mockito.when(internalValidator2.isSupportedType(childRule2)).thenReturn(Boolean.TRUE);

        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).getRules();
        Mockito.verify(internalValidator1).isSupportedType(childRule1);
        Mockito.verify(internalValidator1).validate(childRule1, limit);
        Mockito.verify(internalValidator2).isSupportedType(childRule1);
        Mockito.verify(internalValidator1).isSupportedType(childRule2);
        Mockito.verify(internalValidator2).isSupportedType(childRule2);
        Mockito.verify(internalValidator2).validate(childRule2, limit);
        Mockito.verifyNoMoreInteractions(rule, internalValidator1, internalValidator2);
        Mockito.verifyZeroInteractions(childRule1, childRule2);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleContainsInvalidChildren_throwsException() {
        final RulePropertiesLimitValidator internalValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(Arrays.asList(internalValidator));

        final ComplexRule rule = Mockito.mock(ComplexRule.class);
        final NodeRule childRule1 = Mockito.mock(NodeRule.class);
        final NodeRule childRule2 = Mockito.mock(NodeRule.class);
        Mockito.when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        Mockito.when(internalValidator.isSupportedType(childRule1)).thenReturn(Boolean.TRUE);
        Mockito.when(internalValidator.isSupportedType(childRule2)).thenReturn(Boolean.TRUE);

        final int limit = 10;
        Mockito.doThrow(new ValidationException("error")).when(internalValidator).validate(childRule2, limit);

        validator.validate2(rule, limit);
    }
}
