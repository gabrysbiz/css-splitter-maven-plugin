package biz.gabrys.maven.plugins.css.splitter.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class TokenTypeTest {

    @Test
    public void create_custom() {
        assertThat(TokenType.create("custom")).isSameAs(TokenType.CUSTOM);
    }

    @Test
    public void createFactory_custom_returnsFactory() {
        final TokenValueFactory factory = TokenType.CUSTOM.createFactory();
        assertThat(factory).isExactlyInstanceOf(CustomValueTokenFactory.class);
    }

    @Test
    public void create_date() {
        assertThat(TokenType.create("date")).isSameAs(TokenType.DATE);
    }

    @Test
    public void createFactory_date_returnsFactory() {
        final TokenValueFactory factory = TokenType.DATE.createFactory();
        assertThat(factory).isExactlyInstanceOf(DateTokenValueFactory.class);
    }

    @Test
    public void create_none() {
        assertThat(TokenType.create("none")).isSameAs(TokenType.NONE);
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
