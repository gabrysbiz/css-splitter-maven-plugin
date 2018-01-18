package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;

public final class ComplexRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleDoesNotContainChildren_doesNothing() {
        final List<RulePropertiesLimitValidator> validators = new ArrayList<RulePropertiesLimitValidator>();
        final RulePropertiesLimitValidator internalValidator1 = mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator1);
        final RulePropertiesLimitValidator internalValidator2 = mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator2);

        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(validators);

        final ComplexRule rule = mock(ComplexRule.class);
        when(rule.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        final int limit = 10;

        validator.validate2(rule, limit);

        verify(rule).getRules();
        verifyZeroInteractions(rule, internalValidator1, internalValidator2);
    }

    @Test
    public void validate2_ruleContainsValidChildren_doesNothing() {
        final List<RulePropertiesLimitValidator> validators = new ArrayList<RulePropertiesLimitValidator>();
        final RulePropertiesLimitValidator internalValidator1 = mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator1);
        final RulePropertiesLimitValidator internalValidator2 = mock(RulePropertiesLimitValidator.class);
        validators.add(internalValidator2);

        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(validators);

        final ComplexRule rule = mock(ComplexRule.class);
        final NodeRule childRule1 = mock(NodeRule.class);
        final NodeRule childRule2 = mock(NodeRule.class);
        when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        when(internalValidator1.isSupportedType(childRule1)).thenReturn(Boolean.TRUE);
        when(internalValidator2.isSupportedType(childRule1)).thenReturn(Boolean.FALSE);
        when(internalValidator1.isSupportedType(childRule2)).thenReturn(Boolean.FALSE);
        when(internalValidator2.isSupportedType(childRule2)).thenReturn(Boolean.TRUE);

        final int limit = 10;

        validator.validate2(rule, limit);

        verify(rule).getRules();
        verify(internalValidator1).isSupportedType(childRule1);
        verify(internalValidator1).validate(childRule1, limit);
        verify(internalValidator2).isSupportedType(childRule1);
        verify(internalValidator1).isSupportedType(childRule2);
        verify(internalValidator2).isSupportedType(childRule2);
        verify(internalValidator2).validate(childRule2, limit);
        verifyNoMoreInteractions(rule, internalValidator1, internalValidator2);
        verifyZeroInteractions(childRule1, childRule2);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleContainsInvalidChildren_throwsException() {
        final RulePropertiesLimitValidator internalValidator = mock(RulePropertiesLimitValidator.class);
        final ComplexRulePropertiesLimitValidator validator = new ComplexRulePropertiesLimitValidator(Arrays.asList(internalValidator));

        final ComplexRule rule = mock(ComplexRule.class);
        final NodeRule childRule1 = mock(NodeRule.class);
        final NodeRule childRule2 = mock(NodeRule.class);
        when(rule.getRules()).thenReturn(Arrays.asList(childRule1, childRule2));

        when(internalValidator.isSupportedType(childRule1)).thenReturn(Boolean.TRUE);
        when(internalValidator.isSupportedType(childRule2)).thenReturn(Boolean.TRUE);

        final int limit = 10;
        doThrow(ValidationException.class).when(internalValidator).validate(childRule2, limit);

        validator.validate2(rule, limit);
    }
}
