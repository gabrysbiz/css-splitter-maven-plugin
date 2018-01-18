package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;

import org.junit.Test;

import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class TreeUtilsTest {

    @Test
    public void fillNeighbors_parentIsNull() {
        final NodeRule firstRule = new SupportedTestNodeRule();
        final NodeRule secondRule = new SupportedTestNodeRule();
        final NodeRule thirdRule = new SupportedTestNodeRule();
        final NodeRule fourthRule = new SupportedTestNodeRule();

        TreeUtils.fillNeighbors(null, Arrays.asList(firstRule, secondRule, thirdRule, fourthRule));

        assertThat(firstRule.getParent()).isNull();
        assertThat(firstRule.getPrevious()).isNull();
        assertThat(firstRule.getNext()).isSameAs(secondRule);

        assertThat(secondRule.getParent()).isNull();
        assertThat(secondRule.getPrevious()).isSameAs(firstRule);
        assertThat(secondRule.getNext()).isSameAs(thirdRule);

        assertThat(thirdRule.getParent()).isNull();
        assertThat(thirdRule.getPrevious()).isSameAs(secondRule);
        assertThat(thirdRule.getNext()).isSameAs(fourthRule);

        assertThat(fourthRule.getParent()).isNull();
        assertThat(fourthRule.getPrevious()).isSameAs(thirdRule);
        assertThat(fourthRule.getNext()).isNull();
    }

    @Test
    public void fillNeighbors_parentIsNotNull() {
        final NodeRule parent = mock(NodeRule.class);

        final NodeRule firstRule = new SupportedTestNodeRule();
        final NodeRule secondRule = new SupportedTestNodeRule();
        final NodeRule thirdRule = new SupportedTestNodeRule();
        final NodeRule fourthRule = new SupportedTestNodeRule();

        TreeUtils.fillNeighbors(parent, Arrays.asList(firstRule, secondRule, thirdRule, fourthRule));

        assertThat(firstRule.getParent()).isSameAs(parent);
        assertThat(firstRule.getPrevious()).isNull();
        assertThat(firstRule.getNext()).isSameAs(secondRule);

        assertThat(secondRule.getParent()).isSameAs(parent);
        assertThat(secondRule.getPrevious()).isSameAs(firstRule);
        assertThat(secondRule.getNext()).isSameAs(thirdRule);

        assertThat(thirdRule.getParent()).isSameAs(parent);
        assertThat(thirdRule.getPrevious()).isSameAs(secondRule);
        assertThat(thirdRule.getNext()).isSameAs(fourthRule);

        assertThat(fourthRule.getParent()).isSameAs(parent);
        assertThat(fourthRule.getPrevious()).isSameAs(thirdRule);
        assertThat(fourthRule.getNext()).isNull();

        verifyZeroInteractions(parent);
    }
}
