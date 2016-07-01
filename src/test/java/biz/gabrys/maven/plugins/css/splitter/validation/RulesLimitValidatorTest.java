package biz.gabrys.maven.plugins.css.splitter.validation;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class RulesLimitValidatorTest {

    @Test
    public void validate_valueIsLowerThanLimit_doesNothing() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getSize()).thenReturn(limit - 1);

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getSize();
        Mockito.verifyNoMoreInteractions(stylesheet);
        Mockito.verifyZeroInteractions(stylesheet);
    }

    @Test
    public void validate_valueIsEqualToLimit_doesNothing() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getSize()).thenReturn(limit);

        validator.validate(stylesheet);

        Mockito.verify(stylesheet).getSize();
        Mockito.verifyNoMoreInteractions(stylesheet);
        Mockito.verifyZeroInteractions(stylesheet);
    }

    @Test(expected = ValidationException.class)
    public void validate_valueIsBiggerThanLimit_throwsException() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = Mockito.mock(StyleSheet.class);
        Mockito.when(stylesheet.getSize()).thenReturn(limit + 1);

        validator.validate(stylesheet);
    }
}
