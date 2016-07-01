package biz.gabrys.maven.plugins.css.splitter.css.type;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public final class AbstractNodeRuleTest {

    private static final String CSS_CODE = NodeRuleImpl.class.getName();

    @Test
    public void getCode() {
        final NodeRuleImpl rule = Mockito.spy(new NodeRuleImpl());

        final String code = rule.getCode();

        Assert.assertEquals("Rule code.", CSS_CODE, code);
        Mockito.verify(rule).getCode();
        Mockito.verify(rule).getLines();
        Mockito.verify(rule).fillLines(Matchers.<List<String>>any());
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test
    public void getLines() {
        final NodeRuleImpl rule = Mockito.spy(new NodeRuleImpl());

        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 1, lines.length);
        Assert.assertEquals("1 line.", CSS_CODE, lines[0]);
        Mockito.verify(rule).getLines();
        Mockito.verify(rule).fillLines(Matchers.<List<String>>any());
        Mockito.verifyNoMoreInteractions(rule);
    }

    @Test
    public void getSize() {
        final NodeRuleImpl rule = Mockito.spy(new NodeRuleImpl());

        rule.getSize();
        Mockito.verify(rule).getSize();
        Mockito.verify(rule).getSize2();
        Mockito.verifyNoMoreInteractions(rule);

        rule.getSize();
        Mockito.verify(rule, Mockito.times(2)).getSize();
        Mockito.verify(rule).getSize2();
        Mockito.verifyNoMoreInteractions(rule);
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
