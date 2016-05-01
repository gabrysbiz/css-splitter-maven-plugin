package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.counter.StyleSheetCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

public final class RulesLimitValidatorTest {

    @Test
    public void validate_valueIsLowerThanLimit_doesNothing() throws ValidationException {
        final int limit = 10;
        final StyleSheetCounter counter = Mockito.mock(StyleSheetCounter.class);
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(counter.count(stylesheet)).thenReturn(limit - 1);

        validator.validate(stylesheet);

        Mockito.verify(counter).count(stylesheet);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(stylesheet);
    }

    @Test
    public void validate_valueIsEqualToLimit_doesNothing() throws ValidationException {
        final int limit = 10;
        final StyleSheetCounter counter = Mockito.mock(StyleSheetCounter.class);
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(counter.count(stylesheet)).thenReturn(limit);

        validator.validate(stylesheet);

        Mockito.verify(counter).count(stylesheet);
        Mockito.verifyNoMoreInteractions(counter);
        Mockito.verifyZeroInteractions(stylesheet);
    }

    @Test(expected = ValidationException.class)
    public void validate_valueIsBiggerThanLimit_throwsException() throws ValidationException {
        final int limit = 10;
        final StyleSheetCounter counter = Mockito.mock(StyleSheetCounter.class);
        final RulesLimitValidator validator = new RulesLimitValidator(limit, counter);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(counter.count(stylesheet)).thenReturn(limit + 1);

        validator.validate(stylesheet);
    }
}
