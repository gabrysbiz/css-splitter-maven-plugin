package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

public final class AbstractNodeRuleTest {

    private static final String CSS_CODE = NodeRuleImpl.class.getName();

    @Test
    public void getCode() {
        final NodeRuleImpl rule = spy(new NodeRuleImpl());

        final String code = rule.getCode();

        assertThat(code).isEqualTo(CSS_CODE);
        verify(rule).getCode();
        verify(rule).getLines();
        verify(rule).fillLines(ArgumentMatchers.<List<String>>any());
        verifyNoMoreInteractions(rule);
    }

    @Test
    public void getLines() {
        final NodeRuleImpl rule = spy(new NodeRuleImpl());

        final String[] lines = rule.getLines();

        assertThat(lines).containsExactly(CSS_CODE);
        verify(rule).getLines();
        verify(rule).fillLines(ArgumentMatchers.<List<String>>any());
        verifyNoMoreInteractions(rule);
    }

    @Test
    public void getSize() {
        final NodeRuleImpl rule = spy(new NodeRuleImpl());

        rule.getSize();
        verify(rule).getSize();
        verify(rule).getSize2();
        verifyNoMoreInteractions(rule);

        rule.getSize();
        verify(rule, times(2)).getSize();
        verify(rule).getSize2();
        verifyNoMoreInteractions(rule);
    }

    private static class NodeRuleImpl extends AbstractNodeRule {

        @Override
        protected void fillLines(final List<String> lines) {
            lines.add(CSS_CODE);
        }

        @Override
        protected int getSize2() {
            return 0;
        }
    }
}
