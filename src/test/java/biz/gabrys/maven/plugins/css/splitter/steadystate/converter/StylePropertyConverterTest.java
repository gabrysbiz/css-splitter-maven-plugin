package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;

public final class StylePropertyConverterTest {

    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    public void convert_importantIsFalse() {
        final StylePropertyConverter converter = new StylePropertyConverter();
        final Property property = createProperty(false);

        final StyleProperty styleProperty = converter.convert(property);

        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo(NAME);
        assertThat(styleProperty.getValue()).isEqualTo(VALUE);
        assertThat(styleProperty.isImportant()).isFalse();
    }

    @Test
    public void convert_importantIsTrue() {
        final StylePropertyConverter converter = new StylePropertyConverter();
        final Property property = createProperty(true);

        final StyleProperty styleProperty = converter.convert(property);

        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo(NAME);
        assertThat(styleProperty.getValue()).isEqualTo(VALUE);
        assertThat(styleProperty.isImportant()).isTrue();
    }

    private static Property createProperty(final boolean important) {
        final Property property = new Property();
        property.setName(NAME);
        final CSSValueImpl value = new CSSValueImpl();
        value.setCssText(VALUE);
        property.setValue(value);
        property.setImportant(important);
        return property;
    }
}
