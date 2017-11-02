package biz.gabrys.maven.plugins.css.splitter.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public final class TokenTypeTest {

    @Test
    public void create_custom() {
        assertEquals(TokenType.CUSTOM, TokenType.create("custom"));
    }

    @Test
    public void createFactory_custom_returnsFactory() {
        final TokenValueFactory factory = TokenType.CUSTOM.createFactory();
        assertNotNull("Factory instance should not be equal to null", factory);
        assertEquals("factory class", CustomValueTokenFactory.class, factory.getClass());
    }

    @Test
    public void create_date() {
        assertEquals(TokenType.DATE, TokenType.create("date"));
    }

    @Test
    public void createFactory_date_returnsFactory() {
        final TokenValueFactory factory = TokenType.DATE.createFactory();
        assertNotNull("Factory instance should not be equal to null", factory);
        assertEquals("factory class", DateTokenValueFactory.class, factory.getClass());
    }

    @Test
    public void create_none() {
        assertEquals(TokenType.NONE, TokenType.create("none"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createFactory_custom_throwsException() {
        TokenType.NONE.createFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_unsupportedType_throwsException() {
        TokenType.create("unsupported");
    }
}
