package biz.gabrys.maven.plugins.css.splitter.validation;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

public final class UnknownRulePropertiesLimitValidatorTest {

    @Test
    public void validate2_valueIsLowerThanLimitAndLoggerDisabled_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(rule.size()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(rule).size();
        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(rule, logger);
    }

    @Test
    public void validate2_valueIsLowerThanLimitAndLoggerEnabled_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("CSS code");
        final int limit = 10;
        Mockito.when(rule.size()).thenReturn(limit - 1);

        validator.validate2(rule, limit);

        Mockito.verify(rule).size();
        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
        Mockito.verify(rule).getCode();
        Mockito.verify(logger).debug("Found non-standard (unknown) rule:\nCSS code");
        Mockito.verify(logger).debug("I guess it contains 9 properties.");
        Mockito.verifyNoMoreInteractions(rule, logger);
    }

    @Test
    public void validate2_valueIsEqualToLimitAndLoggerDisabled_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 1;
        Mockito.when(rule.size()).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(rule).size();
        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
        Mockito.verifyNoMoreInteractions(rule, logger);
    }

    @Test
    public void validate2_valueIsEqualToLimitAndLoggerEnabled_doesNothing() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("CSS code");
        final int limit = 1;
        Mockito.when(rule.size()).thenReturn(limit);

        validator.validate2(rule, limit);

        Mockito.verify(rule).size();
        Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
        Mockito.verify(rule).getCode();
        Mockito.verify(logger).debug("Found non-standard (unknown) rule:\nCSS code");
        Mockito.verify(logger).debug("I guess it contains 1 property.");
        Mockito.verifyNoMoreInteractions(rule, logger);
    }

    @Test(expected = ValidationException.class)
    public void validate2_valueIsBiggerThanLimitAndLoggerDisabled_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(false);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        final int limit = 10;
        Mockito.when(rule.size()).thenReturn(limit + 1);

        try {
            validator.validate2(rule, limit);
        } catch (final ValidationException e) {
            Mockito.verify(rule).size();
            Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
            Mockito.verify(rule).getCode();
            Mockito.verifyNoMoreInteractions(rule, logger);
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void validate2_valueIsBiggerThanLimitAndLoggerEnabled_throwsException() {
        final Log logger = Mockito.mock(Log.class);
        final UnknownRulePropertiesLimitValidator validator = new UnknownRulePropertiesLimitValidator(logger);

        Mockito.when(logger.isDebugEnabled()).thenReturn(true);

        final UnknownRule rule = Mockito.mock(UnknownRule.class);
        Mockito.when(rule.getCode()).thenReturn("CSS code");
        final int limit = 10;
        Mockito.when(rule.size()).thenReturn(limit + 1);

        try {
            validator.validate2(rule, limit);
        } catch (final ValidationException e) {
            Mockito.verify(rule).size();
            Mockito.verify(logger, Mockito.times(2)).isDebugEnabled();
            Mockito.verify(rule, Mockito.times(2)).getCode();
            Mockito.verify(logger).debug("Found non-standard (unknown) rule:\nCSS code");
            Mockito.verify(logger).debug("I guess it contains 11 properties.");
            Mockito.verifyNoMoreInteractions(rule, logger);
            throw e;
        }
    }
}
