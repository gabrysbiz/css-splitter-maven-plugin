package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class UnknownRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_valueIsLowerThanLimit_doesNothing() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(rule.getSize()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(rule).getSize();
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_valueIsEqualToLimit_doesNothing() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 1;
        Mockito.when(rule.getSize()).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(rule).getSize();
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate2_valueIsBiggerThanLimit_throwsException() {
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator();

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(rule.getSize()).thenReturn(limit + 1);

        try {
            validator.validate2(rule, limit);
        } catch (final ValidationException e) {
            Mockito.verify(rule).getSize();
            Mockito.verify(rule).getCode();
            Mockito.verifyNoMoreInteractions(rule);
            throw e;
        }
    }
}
