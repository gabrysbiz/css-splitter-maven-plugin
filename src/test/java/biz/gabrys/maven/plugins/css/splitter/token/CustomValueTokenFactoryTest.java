package biz.gabrys.maven.plugins.css.splitter.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class CustomValueTokenFactoryTest {

    @Test
    public void create() {
        final CustomValueTokenFactory factory = new CustomValueTokenFactory();
        final String value = "value";

        final String result = factory.create(value);

        assertThat(result).isSameAs(value);
    }
}
