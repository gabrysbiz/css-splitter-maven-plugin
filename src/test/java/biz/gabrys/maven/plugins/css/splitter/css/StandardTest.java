package biz.gabrys.maven.plugins.css.splitter.css;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class StandardTest {

    @Test
    public void create_standard10() {
        assertEquals(Standard.VERSION_1_0, Standard.create("1.0"));
    }

    @Test
    public void create_standard20() {
        assertEquals(Standard.VERSION_2_0, Standard.create("2.0"));
    }

    @Test
    public void create_standard21() {
        assertEquals(Standard.VERSION_2_1, Standard.create("2.1"));
    }

    @Test
    public void create_standard30() {
        assertEquals(Standard.VERSION_3_0, Standard.create("3.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_unsupportedStandard_throwsException() {
        Standard.create("unsupported");
    }

    @Test
    public void isSameAs() {
        for (final Standard standard : Standard.values()) {
            assertTrue(String.format("Verification for standard %s", standard), standard.isSameAs(standard.toString()));
        }
    }
}
