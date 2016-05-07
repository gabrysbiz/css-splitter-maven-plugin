package biz.gabrys.maven.plugins.css.splitter.css.types;

import org.junit.Test;
import org.mockito.Mockito;

public final class AbstractNodeRuleTest {

    @Test
    public void size() {
        final NodeRuleImpl rule = Mockito.spy(new NodeRuleImpl());

        rule.size();
        Mockito.verify(rule).size();
        Mockito.verify(rule).countSize();
        Mockito.verifyNoMoreInteractions(rule);

        rule.size();
        Mockito.verify(rule, Mockito.times(2)).size();
        Mockito.verify(rule).countSize();
        Mockito.verifyNoMoreInteractions(rule);
    }

    private static class NodeRuleImpl extends AbstractNodeRule {

        @Override
        protected int countSize() {
            return 0;
        }
    }
}
