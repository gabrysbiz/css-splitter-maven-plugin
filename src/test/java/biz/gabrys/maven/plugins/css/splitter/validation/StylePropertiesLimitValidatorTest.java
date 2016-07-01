package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class StylePropertiesLimitValidatorTest {

    @Test
    public void validate_styleSheetIsEmpty_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getRules();
        Mockito.verifyNoMoreInteractions(stylesheet);
        Mockito.verifyZeroInteractions(ruleValidator);
    }

    @Test
    public void validate_styleSheetContainsNotSupportedRules_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(ruleValidator.isSupportedType(rule)).thenReturn(false);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(ruleValidator).isSupportedType(rule);
        Mockito.verifyNoMoreInteractions(stylesheet, ruleValidator);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test
    public void validate_styleSheetContainsValidRule_doesNothing() {
        final RulePropertiesLimitValidator ruleValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(ruleValidator.isSupportedType(rule)).thenReturn(true);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(ruleValidator).isSupportedType(rule);
        Mockito.verify(ruleValidator).validate(rule, limit);
        Mockito.verifyNoMoreInteractions(stylesheet, ruleValidator);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate_styleSheetContainsInvalidRule_throwsException() {
        final RulePropertiesLimitValidator ruleValidator = Mockito.mock(RulePropertiesLimitValidator.class);
        final int limit = 10;
        final StylePropertiesLimitValidator validator = new StylePropertiesLimitValidator(Arrays.asList(ruleValidator), limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(ruleValidator.isSupportedType(rule)).thenReturn(true);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        Mockito.doThrow(new ValidationException("error")).when(ruleValidator).validate(rule, limit);

        validator.validate(stylesheet);
    }
}
