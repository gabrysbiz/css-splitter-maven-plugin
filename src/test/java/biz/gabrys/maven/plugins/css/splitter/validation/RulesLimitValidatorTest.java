package biz.gabrys.maven.plugins.css.splitter.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public final class RulesLimitValidatorTest {

    @Test
    public void validate_valueIsLowerThanLimit_doesNothing() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        when(stylesheet.getSize()).thenReturn(limit - 1);

        validator.validate(stylesheet);

        verify(stylesheet).getSize();
        verifyNoMoreInteractions(stylesheet);
        verifyZeroInteractions(stylesheet);
    }

    @Test
    public void validate_valueIsEqualToLimit_doesNothing() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        when(stylesheet.getSize()).thenReturn(limit);

        validator.validate(stylesheet);

        verify(stylesheet).getSize();
        verifyNoMoreInteractions(stylesheet);
        verifyZeroInteractions(stylesheet);
    }

    @Test(expected = ValidationException.class)
    public void validate_valueIsBiggerThanLimit_throwsException() {
        final int limit = 10;
        final RulesLimitValidator validator = new RulesLimitValidator(limit);

        final StyleSheet stylesheet = mock(StyleSheet.class);
        when(stylesheet.getSize()).thenReturn(limit + 1);

        validator.validate(stylesheet);
    }
}
