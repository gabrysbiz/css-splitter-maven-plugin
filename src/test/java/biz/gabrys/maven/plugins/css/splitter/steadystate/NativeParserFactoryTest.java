package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.css.sac.Parser;

import com.steadystate.css.parser.SACParserCSS1;
import com.steadystate.css.parser.SACParserCSS2;
import com.steadystate.css.parser.SACParserCSS21;
import com.steadystate.css.parser.SACParserCSS3;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

public final class NativeParserFactoryTest {

    @Test
    public void create_checkIfSupportAllStandards() {
        final NativeParserFactory factory = new NativeParserFactory();
        for (final Standard standard : Standard.values()) {
            try {
                final Parser parser = factory.create(standard);
                Assert.assertNotNull(String.format("Parser for standard \"%s\".", standard), parser);
            } catch (final Exception e) {
                Assert.fail(String.format("Factory threw exception for standard \"%s\". %s", standard, e));
            }
        }
    }

    @Test
    public void create_standard10_returnsInstanceOfSACParserCSS1() {
        final NativeParserFactory factory = new NativeParserFactory();
        final Parser parser = factory.create(Standard.VERSION_1_0);
        Assert.assertNotNull("Parser instance.", parser);
        Assert.assertEquals("Parser class.", SACParserCSS1.class, parser.getClass());
    }

    @Test
    public void create_standard20_returnsInstanceOfSACParserCSS20() {
        final NativeParserFactory factory = new NativeParserFactory();
        final Parser parser = factory.create(Standard.VERSION_2_0);
        Assert.assertNotNull("Parser instance.", parser);
        Assert.assertEquals("Parser class.", SACParserCSS2.class, parser.getClass());
    }

    @Test
    public void create_standard21_returnsInstanceOfSACParserCSS21() {
        final NativeParserFactory factory = new NativeParserFactory();
        final Parser parser = factory.create(Standard.VERSION_2_1);
        Assert.assertNotNull("Parser instance.", parser);
        Assert.assertEquals("Parser class.", SACParserCSS21.class, parser.getClass());
    }

    @Test
    public void create_standard30_returnsInstanceOfSACParserCSS3() {
        final NativeParserFactory factory = new NativeParserFactory();
        final Parser parser = factory.create(Standard.VERSION_3_0);
        Assert.assertNotNull("Parser instance.", parser);
        Assert.assertEquals("Parser class.", SACParserCSS3.class, parser.getClass());
    }
}
