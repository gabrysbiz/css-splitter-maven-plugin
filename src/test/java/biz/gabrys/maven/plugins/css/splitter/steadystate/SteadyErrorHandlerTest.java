package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.w3c.css.sac.CSSParseException;

public final class SteadyErrorHandlerTest {

    @Test
    public void validate_warningOccured_doesNothing() {
        final Log logger = mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = mock(CSSParseException.class);
        when(exception.getLineNumber()).thenReturn(2);
        when(exception.getColumnNumber()).thenReturn(3);
        when(exception.getMessage()).thenReturn("warning message");

        handler.warning(exception);
        verify(logger).warn("[2:3] warning message");
        verifyNoMoreInteractions(logger);

        handler.validate();
    }

    @Test(expected = ParserException.class)
    public void validate_errorOccured_throwsException() {
        final Log logger = mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = mock(CSSParseException.class);
        when(exception.getLineNumber()).thenReturn(1);
        when(exception.getColumnNumber()).thenReturn(2);
        when(exception.getMessage()).thenReturn("error message");

        handler.error(exception);
        verify(logger).error("[1:2] error message");
        verifyNoMoreInteractions(logger);

        handler.validate();
    }

    @Test(expected = ParserException.class)
    public void validate_fatalErrorOccured_throwsException() {
        final Log logger = mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = mock(CSSParseException.class);
        when(exception.getLineNumber()).thenReturn(3);
        when(exception.getColumnNumber()).thenReturn(1);
        when(exception.getMessage()).thenReturn("fatal error message");

        handler.fatalError(exception);
        verify(logger).error("[3:1] fatal error message");
        verifyNoMoreInteractions(logger);

        handler.validate();
    }
}
