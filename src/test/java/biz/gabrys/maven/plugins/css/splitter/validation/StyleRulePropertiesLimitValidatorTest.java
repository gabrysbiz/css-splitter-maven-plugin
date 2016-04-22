package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class StyleRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleIsSplittable_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator(counter);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(true);
        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verifyNoMoreInteractions(rule);
        Mockito.verifyZeroInteractions(counter);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsLowerThanLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator(counter);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(rule, counter);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsEqualToLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator(counter);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(rule, counter);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleIsNotSplittableAndValueIsBiggerThanLimit_throwsException() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator(counter);

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(counter.count(rule)).thenReturn(limit + 1);

        validator.validate2(rule, limit);
    }
}
