package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.UnknownRule;

public final class UnknownRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_valueIsLowerThanLimit_doesNothing() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = mock(UnknownRule.class);
        final int limit = 10;
        when(rule.getSize()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        verify(rule).getSize();
        verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_valueIsEqualToLimit_doesNothing() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = mock(UnknownRule.class);
        final int limit = 1;
        when(rule.getSize()).thenReturn(limit);

        validator.validate2(rule, limit);

        verify(rule).getSize();
        verifyNoMoreInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate2_valueIsBiggerThanLimit_throwsException() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = mock(UnknownRule.class);
        final int limit = 10;
        when(rule.getSize()).thenReturn(limit + 1);

        try {
            validator.validate2(rule, limit);
        } catch (final ValidationException e) {
            verify(rule).getSize();
            verify(rule).getCode();
            verifyNoMoreInteractions(rule);
            throw e;
        }
    }
}
