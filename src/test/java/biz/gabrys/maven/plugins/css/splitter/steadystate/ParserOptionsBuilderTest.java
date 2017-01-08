package biz.gabrys.maven.plugins.css.splitter.steadystate;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

public final class ParserOptionsBuilderTest {

    @Test
    public void create_checkStandards() {
        for (final Standard standard : Standard.values()) {
            final ParserOptions options = new ParserOptionsBuilder().withStandard(standard).create();
            Assert.assertEquals("Standard.", standard, options.getStandard());
        }
    }

    @Test
    public void create_checkStrict() {
        for (final boolean strict : Arrays.asList(true, false)) {
            final ParserOptions options = new ParserOptionsBuilder().withStrict(strict).create();
            Assert.assertEquals("Strict value.", strict, options.isStrict());
        }
    }

    @Test
    public void create_checkStarHack() {
        for (final boolean allowed : Arrays.asList(true, false)) {
            final ParserOptions options = new ParserOptionsBuilder().withStarHack(allowed).create();
            Assert.assertEquals("Star hack value.", allowed, options.isStarHackAllowed());
        }
    }
}
