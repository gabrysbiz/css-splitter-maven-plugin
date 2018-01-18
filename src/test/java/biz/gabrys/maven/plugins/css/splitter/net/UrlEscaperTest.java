package biz.gabrys.maven.plugins.css.splitter.net;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class UrlEscaperTest {

    @Test
    public void escape() {
        final String escaped = UrlEscaper.escape("TEST=\"asdas'&!+ -~[]()");
        assertThat(escaped).isEqualTo("TEST%3D%22asdas%27%26%21%2B%20-%7E%5B%5D%28%29");
    }
}
