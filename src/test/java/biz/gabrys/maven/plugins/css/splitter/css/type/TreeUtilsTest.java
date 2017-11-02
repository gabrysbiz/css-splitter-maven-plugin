package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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

        assertNull("First rule parent invalid", firstRule.getParent());
        assertNull("First rule previous invalid", firstRule.getPrevious());
        assertSame("First rule next invalid", secondRule, firstRule.getNext());

        assertNull("Second rule parent invalid", secondRule.getParent());
        assertSame("Second rule previous invalid", firstRule, secondRule.getPrevious());
        assertSame("Second rule next invalid", thirdRule, secondRule.getNext());

        assertNull("Third rule parent invalid", thirdRule.getParent());
        assertSame("Third rule previous invalid", secondRule, thirdRule.getPrevious());
        assertSame("Third rule next invalid", fourthRule, thirdRule.getNext());

        assertNull("Fourth rule parent invalid", fourthRule.getParent());
        assertSame("Fourth rule previous invalid", thirdRule, fourthRule.getPrevious());
        assertNull("Fourth rule next invalid", fourthRule.getNext());
    }

    @Test
    public void fillNeighbors_parentIsNotNull() {
        final NodeRule parent = mock(NodeRule.class);

        final NodeRule firstRule = new SupportedTestNodeRule();
        final NodeRule secondRule = new SupportedTestNodeRule();
        final NodeRule thirdRule = new SupportedTestNodeRule();
        final NodeRule fourthRule = new SupportedTestNodeRule();

        TreeUtils.fillNeighbors(parent, Arrays.asList(firstRule, secondRule, thirdRule, fourthRule));

        assertSame("First rule parent invalid", parent, firstRule.getParent());
        assertNull("First rule previous invalid", firstRule.getPrevious());
        assertSame("First rule next invalid", secondRule, firstRule.getNext());

        assertSame("Second rule parent invalid", parent, secondRule.getParent());
        assertSame("Second rule previous invalid", firstRule, secondRule.getPrevious());
        assertSame("Second rule next invalid", thirdRule, secondRule.getNext());

        assertSame("Third rule parent invalid", parent, thirdRule.getParent());
        assertSame("Third rule previous invalid", secondRule, thirdRule.getPrevious());
        assertSame("Third rule next invalid", fourthRule, thirdRule.getNext());

        assertSame("Fourth rule parent invalid", parent, fourthRule.getParent());
        assertSame("Fourth rule previous invalid", thirdRule, fourthRule.getPrevious());
        assertNull("Fourth rule next invalid", fourthRule.getNext());

        verifyZeroInteractions(parent);
    }
}
