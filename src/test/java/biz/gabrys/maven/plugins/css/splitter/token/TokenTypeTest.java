package biz.gabrys.maven.plugins.css.splitter.token;

import org.junit.Assert;
import org.junit.Test;

public final class TokenTypeTest {

    @Test
    public void create_custom() {
        Assert.assertEquals("Should return \"custom\"", TokenType.CUSTOM, TokenType.create("custom"));
    }

    @Test
    public void createFactory_custom_returnsFactory() {
        final TokenValueFactory factory = TokenType.CUSTOM.createFactory();
        Assert.assertNotNull("Factory instance.", factory);
        Assert.assertEquals("factory class.", CustomValueTokenFactory.class, factory.getClass());
    }

    @Test
    public void create_date() {
        Assert.assertEquals("Should return \"date\".", TokenType.DATE, TokenType.create("date"));
    }

    @Test
    public void createFactory_date_returnsFactory() {
        final TokenValueFactory factory = TokenType.DATE.createFactory();
        Assert.assertNotNull("Factory instance.", factory);
        Assert.assertEquals("factory class.", DateTokenValueFactory.class, factory.getClass());
    }

    @Test
    public void create_none() {
        Assert.assertEquals("Should return \"none\".", TokenType.NONE, TokenType.create("none"));
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
