package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

public final class ParserOptionsBuilderTest {

    @Test
    public void create_checkStandards() {
        for (final Standard standard : Standard.values()) {
            final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).create();
            assertThat(options.getStandard()).isSameAs(standard);
        }
    }

    @Test
    public void create_checkStrict() {
        for (final boolean strict : Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            final ParserOptions options = new ParserOptionsBuilder().withStrict(strict).create();
            assertThat(options.isStrict()).isEqualTo(strict);
        }
    }

    @Test
    public void create_checkStarHack() {
        for (final boolean allowed : Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            final ParserOptions options = new ParserOptionsBuilder().withStarHack(allowed).create();
            assertThat(options.isStarHackAllowed()).isEqualTo(allowed);
        }
    }
}
