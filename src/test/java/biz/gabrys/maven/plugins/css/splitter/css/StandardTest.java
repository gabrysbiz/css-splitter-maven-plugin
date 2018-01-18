package biz.gabrys.maven.plugins.css.splitter.css;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class StandardTest {

    @Test
    public void create_standard10() {
        assertThat(Standard.create("1.0")).isSameAs(Standard.VERSION_1_0);
    }

    @Test
    public void create_standard20() {
        assertThat(Standard.create("2.0")).isSameAs(Standard.VERSION_2_0);
    }

    @Test
    public void create_standard21() {
        assertThat(Standard.create("2.1")).isSameAs(Standard.VERSION_2_1);
    }

    @Test
    public void create_standard30() {
        assertThat(Standard.create("3.0")).isSameAs(Standard.VERSION_3_0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_unsupportedStandard_throwsException() {
        Standard.create("unsupported");
    }

    @Test
    public void isSameAs() {
        for (final Standard standard : Standard.values()) {
            final boolean same = standard.isSameAs(standard.toString());
            assertThat(same).overridingErrorMessage("Verification for standard %s returns false", standard).isTrue();
        }
    }
}
