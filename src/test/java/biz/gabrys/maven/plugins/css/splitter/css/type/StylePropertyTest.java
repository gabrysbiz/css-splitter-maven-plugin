package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class StylePropertyTest {

    @Test
    public void getCode_importantIsFalse_returnsNameAndValue() {
        final StyleProperty property = new StyleProperty("width", "100px", false);
        final String code = property.getCode();
        assertThat(code).isEqualTo("width: 100px;");
    }

    @Test
    public void getCode_importantIsTrue_returnsNameAndValueWithImportant() {
        final StyleProperty property = new StyleProperty("content", "\"text\"", true);
        final String code = property.getCode();
        assertThat(code).isEqualTo("content: \"text\" !important;");
    }
}
