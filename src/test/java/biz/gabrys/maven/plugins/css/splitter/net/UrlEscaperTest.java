package biz.gabrys.maven.plugins.css.splitter.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class UrlEscaperTest {

    @Test
    public void escape() {
        final String escaped = UrlEscaper.escape("TEST=\"asdas'&!+ -~[]()");
        assertEquals("TEST%3D%22asdas%27%26%21%2B%20-%7E%5B%5D%28%29", escaped);
    }
}
