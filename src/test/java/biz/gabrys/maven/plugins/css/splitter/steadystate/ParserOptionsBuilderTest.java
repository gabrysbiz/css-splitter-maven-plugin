package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public final class ParserOptionsBuilderTest {

    @Test
    @Parameters(method = "allStandards")
    public void create_checkStandards(final Standard standard) {
        final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).create();
        assertThat(options.getStandard()).isSameAs(standard);
    }

    public static Standard[] allStandards() {
        return Standard.values();
    }

    @Test
    @Parameters({ "true", "false" })
    public void create_checkStrict(final boolean strict) {
        final ParserOptions options = new ParserOptionsBuilder().withStrict(strict).create();
        assertThat(options.isStrict()).isEqualTo(strict);
    }

    @Test
    @Parameters({ "true", "false" })
    public void create_checkStarHack(final boolean allowed) {
        final ParserOptions options = new ParserOptionsBuilder().withStarHack(allowed).create();
        assertThat(options.isStarHackAllowed()).isEqualTo(allowed);
    }
}
