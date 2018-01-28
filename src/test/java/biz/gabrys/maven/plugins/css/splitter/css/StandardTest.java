package biz.gabrys.maven.plugins.css.splitter.css;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
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
    @Parameters(method = "allStandards")
    public void isSameAs(final Standard standard) {
        final boolean same = standard.isSameAs(standard.toString());
        assertThat(same).isTrue();
    }

    public static Standard[] allStandards() {
        return Standard.values();
    }
}
