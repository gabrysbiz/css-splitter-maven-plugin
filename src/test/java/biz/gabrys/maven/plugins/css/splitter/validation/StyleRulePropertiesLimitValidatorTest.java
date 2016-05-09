package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public final class StyleRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleIsSplittable_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(true);
        final int limit = 10;

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsLowerThanLimit_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(rule.getSize()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verify(rule).getSize();
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsEqualToLimit_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(rule.getSize()).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(rule).isSplittable();
        Mockito.verify(rule).getSize();
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleIsNotSplittableAndValueIsBiggerThanLimit_throwsException() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = Mockito.mock(StyleRule.class);
        Mockito.when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        Mockito.when(rule.getSize()).thenReturn(limit + 1);

        validator.validate2(rule, limit);
    }
}
