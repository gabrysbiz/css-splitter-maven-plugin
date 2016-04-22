package biz.gabrys.maven.plugins.css.splitter.token;

import org.junit.Assert;
import org.junit.Test;

public final class CustomValueTokenFactoryTest {

    @Test
    public void create() {
        final CustomValueTokenFactory factory = new CustomValueTokenFactory();
        final String value = "value";

        Assert.assertTrue("Instances should be the same", value == factory.create(value));
    }
}
