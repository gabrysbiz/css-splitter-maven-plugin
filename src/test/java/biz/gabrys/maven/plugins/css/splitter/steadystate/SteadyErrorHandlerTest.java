package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.css.sac.CSSParseException;

public final class SteadyErrorHandlerTest {

    @Test
    public void validate_warningOccured_doesNothing() throws ParserException {
        final Log logger = Mockito.mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = Mockito.mock(CSSParseException.class);
        Mockito.when(exception.getLineNumber()).thenReturn(2);
        Mockito.when(exception.getColumnNumber()).thenReturn(3);
        Mockito.when(exception.getMessage()).thenReturn("warning message");

        handler.warning(exception);
        Mockito.verify(logger).warn("[2:3] warning message");
        Mockito.verifyNoMoreInteractions(logger);

        handler.validate();
    }

    @Test(expected = ParserException.class)
    public void validate_errorOccured_throwsException() throws ParserException {
        final Log logger = Mockito.mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = Mockito.mock(CSSParseException.class);
        Mockito.when(exception.getLineNumber()).thenReturn(1);
        Mockito.when(exception.getColumnNumber()).thenReturn(2);
        Mockito.when(exception.getMessage()).thenReturn("error message");

        handler.error(exception);
        Mockito.verify(logger).error("[1:2] error message");
        Mockito.verifyNoMoreInteractions(logger);

        handler.validate();
    }

    @Test(expected = ParserException.class)
    public void validate_fatalErrorOccured_throwsException() throws ParserException {
        final Log logger = Mockito.mock(Log.class);
        final SteadyErrorHandler handler = new SteadyErrorHandler(logger);

        final CSSParseException exception = Mockito.mock(CSSParseException.class);
        Mockito.when(exception.getLineNumber()).thenReturn(3);
        Mockito.when(exception.getColumnNumber()).thenReturn(1);
        Mockito.when(exception.getMessage()).thenReturn("fatal error message");

        handler.fatalError(exception);
        Mockito.verify(logger).error("[3:1] fatal error message");
        Mockito.verifyNoMoreInteractions(logger);

        handler.validate();
    }
}
