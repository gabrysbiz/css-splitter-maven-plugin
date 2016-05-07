package biz.gabrys.maven.plugins.css.splitter.css.types;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.maven.plugins.css.splitter.test.SupportedTestNodeRule;

public final class TreeUtilsTest {

    @Test
    public void fillNeighbors_parentIsNull() {
        final NodeRule firstRule = new SupportedTestNodeRule();
        final NodeRule secondRule = new SupportedTestNodeRule();
        final NodeRule thirdRule = new SupportedTestNodeRule();
        final NodeRule fourthRule = new SupportedTestNodeRule();

        TreeUtils.fillNeighbors(null, Arrays.asList(firstRule, secondRule, thirdRule, fourthRule));

        Assert.assertNull("First rule parent invalid", firstRule.getParent());
        Assert.assertNull("First rule previous invalid", firstRule.getPrevious());
        Assert.assertTrue("First rule next invalid", secondRule == firstRule.getNext());

        Assert.assertNull("Second rule parent invalid", secondRule.getParent());
        Assert.assertTrue("Second rule previous invalid", firstRule == secondRule.getPrevious());
        Assert.assertTrue("Second rule next invalid", thirdRule == secondRule.getNext());

        Assert.assertNull("Third rule parent invalid", thirdRule.getParent());
        Assert.assertTrue("Third rule previous invalid", secondRule == thirdRule.getPrevious());
        Assert.assertTrue("Third rule next invalid", fourthRule == thirdRule.getNext());

        Assert.assertNull("Fourth rule parent invalid", fourthRule.getParent());
        Assert.assertTrue("Fourth rule previous invalid", thirdRule == fourthRule.getPrevious());
        Assert.assertNull("Fourth rule next invalid", fourthRule.getNext());
    }

    @Test
    public void fillNeighbors_parentIsNotNull() {
        final NodeRule parent = Mockito.mock(NodeRule.class);

        final NodeRule firstRule = new SupportedTestNodeRule();
        final NodeRule secondRule = new SupportedTestNodeRule();
        final NodeRule thirdRule = new SupportedTestNodeRule();
        final NodeRule fourthRule = new SupportedTestNodeRule();

        TreeUtils.fillNeighbors(parent, Arrays.asList(firstRule, secondRule, thirdRule, fourthRule));

        Assert.assertTrue("First rule parent invalid", parent == firstRule.getParent());
        Assert.assertNull("First rule previous invalid", firstRule.getPrevious());
        Assert.assertTrue("First rule next invalid", secondRule == firstRule.getNext());

        Assert.assertTrue("Second rule parent invalid", parent == secondRule.getParent());
        Assert.assertTrue("Second rule previous invalid", firstRule == secondRule.getPrevious());
        Assert.assertTrue("Second rule next invalid", thirdRule == secondRule.getNext());

        Assert.assertTrue("Third rule parent invalid", parent == thirdRule.getParent());
        Assert.assertTrue("Third rule previous invalid", secondRule == thirdRule.getPrevious());
        Assert.assertTrue("Third rule next invalid", fourthRule == thirdRule.getNext());

        Assert.assertTrue("Fourth rule parent invalid", parent == fourthRule.getParent());
        Assert.assertTrue("Fourth rule previous invalid", thirdRule == fourthRule.getPrevious());
        Assert.assertNull("Fourth rule next invalid", fourthRule.getNext());

        Mockito.verifyZeroInteractions(parent);
    }
}
