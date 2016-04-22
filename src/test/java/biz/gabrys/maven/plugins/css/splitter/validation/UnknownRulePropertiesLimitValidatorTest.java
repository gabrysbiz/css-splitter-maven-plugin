package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class UnknownRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_valueIsLowerThanLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(counter);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test
    public void validate2_valueIsEqualToLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(counter);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate2_valueIsBiggerThanLimit_throwsException() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(counter);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit + 1);

        validator.validate2(rule, limit);
    }
}
