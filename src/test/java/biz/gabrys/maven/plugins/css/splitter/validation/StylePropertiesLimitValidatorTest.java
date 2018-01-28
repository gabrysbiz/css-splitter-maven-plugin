package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StylePropertiesLimitValidatorTest {

    @Test
    public void validate_styleSheetIsEmpty_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        validator.validate(stylesheet);

        verify(stylesheet).getRules();
        verifyNoMoreInteractions(stylesheet);
        verifyZeroInteractions(ruleValidator);
    }

    @Test
    public void validate_styleSheetContainsNotSupportedRules_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule = mock(NodeRule.class);
        when(ruleValidator.isSupportedType(rule)).thenReturn(false);
        when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        validator.validate(stylesheet);

        verify(stylesheet).getRules();
        verify(ruleValidator).isSupportedType(rule);
        verifyNoMoreInteractions(stylesheet, ruleValidator);
        verifyZeroInteractions(rule);
    }

    @Test
    public void validate_styleSheetContainsValidRule_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule = mock(NodeRule.class);
        when(ruleValidator.isSupportedType(rule)).thenReturn(true);
        when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        validator.validate(stylesheet);

        verify(stylesheet).getRules();
        verify(ruleValidator).isSupportedType(rule);
        verify(ruleValidator).validate(rule, limit);
        verifyNoMoreInteractions(stylesheet, ruleValidator);
        verifyZeroInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate_styleSheetContainsInvalidRule_throwsException() {
        final RulePropertiesLimitValidator ruleValidator = mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        final NodeRule rule = mock(NodeRule.class);
        when(ruleValidator.isSupportedType(rule)).thenReturn(true);
        when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        doThrow(ValidationException.class).when(ruleValidator).validate(rule, limit);

        validator.validate(stylesheet);
    }
}
