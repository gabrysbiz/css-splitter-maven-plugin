package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleRule;

public final class StyleRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_ruleIsSplittable_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(true);
        final int limit = 10;

        validator.validate2(rule, limit);

        verify(rule).isSplittable();
        verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsLowerThanLimit_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        when(rule.getSize()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        verify(rule).isSplittable();
        verify(rule).getSize();
        verifyNoMoreInteractions(rule);
    }

    @Test
    public void validate2_ruleIsNotSplittableAndValueIsEqualToLimit_doesNothing() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        when(rule.getSize()).thenReturn(limit);

        validator.validate2(rule, limit);

        verify(rule).isSplittable();
        verify(rule).getSize();
        verifyNoMoreInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate2_ruleIsNotSplittableAndValueIsBiggerThanLimit_throwsException() {
        final StyleRulePropertiesLimitValidator validator = new StyleRulePropertiesLimitValidator();

        final StyleRule rule = mock(StyleRule.class);
        when(rule.isSplittable()).thenReturn(false);
        final int limit = 10;
        when(rule.getSize()).thenReturn(limit + 1);

        validator.validate2(rule, limit);
    }
}
