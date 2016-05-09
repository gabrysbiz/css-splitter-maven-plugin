package biz.gabrys.maven.plugins.css.splitter.css;

import org.junit.Assert;
import org.junit.Test;

public final class StandardTest {

    @Test
    public void create_standard10() {
        Assert.assertEquals("Should return 1.0 standard.", Standard.VERSION_1_0, Standard.create("1.0"));
    }

    @Test
    public void create_standard20() {
        Assert.assertEquals("Should return 2.0 standard.", Standard.VERSION_2_0, Standard.create("2.0"));
    }

    @Test
    public void create_standard21() {
        Assert.assertEquals("Should return 2.1 standard.", Standard.VERSION_2_1, Standard.create("2.1"));
    }

    @Test
    public void create_standard30() {
        Assert.assertEquals("Should return 3.0 standard.", Standard.VERSION_3_0, Standard.create("3.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_unsupportedStandard_throwsException() {
        Standard.create("unsupported");
    }
}
