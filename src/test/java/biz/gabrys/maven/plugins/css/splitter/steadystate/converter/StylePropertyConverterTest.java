package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.Property;

import biz.gabrys.maven.plugins.css.splitter.css.type.StyleProperty;

public final class StylePropertyConverterTest {

    private static final String NAME = "name";
    private static final String VALUE = "\"value\"";

    @Test
    public void convert_importantIsFalse() {
        final CssFormatter formatter = mock(CssFormatter.class);
        final StylePropertyConverter converter = new StylePropertyConverter(formatter);
        final Property property = createProperty(false);
        doReturn(VALUE).when(formatter).format(property.getValue());

        final StyleProperty styleProperty = converter.convert(property);

        assertThat(styleProperty).isNotNull();
        assertThat(styleProperty.getName()).isEqualTo(NAME);
        assertThat(styleProperty.getValue()).isEqualTo(VALUE);
        assertThat(styleProperty.isImportant()).isFalse();
    }

    @Test
    public void convert_importantIsTrue() {
        final CssFormatter formatter = mock(CssFormatter.class);
        final StylePropertyConverter converter = new StylePropertyConverter(formatter);
        final Property property = createProperty(true);
        doReturn(VALUE).when(formatter).format(property.getValue());

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
