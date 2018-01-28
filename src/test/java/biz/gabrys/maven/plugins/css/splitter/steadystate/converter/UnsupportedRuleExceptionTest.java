package biz.gabrys.maven.plugins.css.splitter.steadystate.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.w3c.dom.css.CSSRule;

public final class UnsupportedRuleExceptionTest {

    @Test
    public void createMessage() {
        final CSSRule rule = mock(CSSRule.class);
        final CssFormatter formatter = mock(CssFormatter.class);
        when(rule.getType()).thenReturn((short) 4);
        when(formatter.format(rule)).thenReturn("<code>");

        final String message = UnsupportedRuleException.createMessage(rule, formatter);

        assertThat(message).isEqualTo(String.format(
                "Rule represented by \"%s\" class (type: 4) is unsupported in current contex! CSS code that causes error:%n<code>",
                rule.getClass().getName()));
    }
}
