package biz.gabrys.maven.plugins.css.splitter.token;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public final class CustomValueTokenFactoryTest {

    @Test
    public void create() {
        final CustomValueTokenFactory factory = new CustomValueTokenFactory();
        final String value = "value";
        assertSame(value, factory.create(value));
    }
}
