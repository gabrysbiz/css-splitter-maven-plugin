package biz.gabrys.maven.plugins.css.splitter.validation;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class RulesLimitValidatorTest {

    @Test
    public void count_styleSheetIsEmpty_returnsZero() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Collections.<NodeRule>emptyList());

        Assert.assertEquals("Should return 0 for empty StyleSheet.", 0, validator.count(stylesheet));
        Mockito.verify(stylesheet).getRules();
        Mockito.verifyNoMoreInteractions(stylesheet);
        Mockito.verifyZeroInteractions(counter);
    }

    @Test
    public void count_styleSheetIsNotEmpty_returnsCountedValue() {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule1 = Mockito.mock(NodeRule.class);
        final NodeRule rule2 = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule1, rule2));

        final int value1 = 3;
        Mockito.when(counter.count(rule1)).thenReturn(value1);
        final int value2 = 5;
        Mockito.when(counter.count(rule2)).thenReturn(value2);
        final int sum = value1 + value2;

        Assert.assertEquals("Should return sum for two rules.", sum, validator.count(stylesheet));

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(counter).count(rule1);
        Mockito.verify(counter).count(rule2);
        Mockito.verifyNoMoreInteractions(stylesheet, counter);
        Mockito.verifyZeroInteractions(rule1, rule2);
    }

    @Test
    public void validate_valueIsLowerThanLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        Mockito.when(counter.count(rule)).thenReturn(limit - 1);

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(stylesheet, counter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test
    public void validate_valueIsEqualToLimit_doesNothing() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        Mockito.when(counter.count(rule)).thenReturn(limit);

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getRules();
        Mockito.verify(counter).count(rule);
        Mockito.verifyNoMoreInteractions(stylesheet, counter);
        Mockito.verifyZeroInteractions(rule);
    }

    @Test(expected = ValidationException.class)
    public void validate_valueIsBiggerThanLimit_throwsException() throws ValidationException {
        final RuleCounter counter = Mockito.mock(RuleCounter.class);
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        final NodeRule rule = Mockito.mock(NodeRule.class);
        Mockito.when(stylesheet.getRules()).thenReturn(Arrays.asList(rule));

        Mockito.when(counter.count(rule)).thenReturn(limit + 1);

        validator.validate(stylesheet);
    }
}
