package biz.gabrys.maven.plugins.css.splitter.steadystate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import com.steadystate.css.parser.SACParser;
import com.steadystate.css.parser.SACParserCSS1;
import com.steadystate.css.parser.SACParserCSS2;
import com.steadystate.css.parser.SACParserCSS21;
import com.steadystate.css.parser.SACParserCSS3;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

public final class SACParserFactoryTest {

    @Test
    public void create_checkIfSupportAllStandards() {
        final SACParserFactory factory = new SACParserFactory();
        for (final Standard standard : Standard.values()) {
            try {
                final SACParser parser = factory.create(standard);
                assertThat(parser).overridingErrorMessage("Parser for standard %s should not be equal to null", standard).isNotNull();
            } catch (final Exception e) {
                fail(String.format("Factory threw exception for standard %s. %s", standard, e));
            }
        }
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
