package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.steadystate.css.parser.SACParser;
import com.steadystate.css.parser.SACParserCSS1;
import com.steadystate.css.parser.SACParserCSS2;
import com.steadystate.css.parser.SACParserCSS21;
import com.steadystate.css.parser.SACParserCSS3;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public final class SACParserFactoryTest {

    @Test
    @Parameters(method = "allStandards")
    public void create_checkIfSupportAllStandards(final Standard standard) {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser parser = factory.create(standard);
        assertThat(parser).isNotNull();
    }

    public static Standard[] allStandards() {
        return Standard.values();
    }

    @Test
    public void create_standard10_returnsInstanceOfSACParserCSS1() {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser parser = factory.create(Standard.VERSION_1_0);
        assertThat(parser).isExactlyInstanceOf(SACParserCSS1.class);
    }

    @Test
    public void create_standard20_returnsInstanceOfSACParserCSS20() {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser parser = factory.create(Standard.VERSION_2_0);
        assertThat(parser).isExactlyInstanceOf(SACParserCSS2.class);
    }

    @Test
    public void create_standard21_returnsInstanceOfSACParserCSS21() {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser parser = factory.create(Standard.VERSION_2_1);
        assertThat(parser).isExactlyInstanceOf(SACParserCSS21.class);
    }

    @Test
    public void create_standard30_returnsInstanceOfSACParserCSS3() {
        final SACParserFactory factory = new SACParserFactory();
        final SACParser parser = factory.create(Standard.VERSION_3_0);
        assertThat(parser).isExactlyInstanceOf(SACParserCSS3.class);
    }
}
