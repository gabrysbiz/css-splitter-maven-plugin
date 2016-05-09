package biz.gabrys.maven.plugins.css.splitter.css.types;

import org.junit.Assert;
import org.junit.Test;

public final class UnknownRuleTest {

    @Test
    public void getLines() {
        final StringBuilder code = new StringBuilder();
        code.append("@keyframes rotate {\n");
        code.append("from  {\n");
        code.append("  transform: rotate(0deg);\n");
        code.append("}\n");
        code.append("to {\n");
        code.append("  transform: rotate(360deg);\n");
        code.append("}\n");
        code.append("}");

        final UnknownRule rule = new UnknownRule(code.toString());
        final String[] lines = rule.getLines();

        Assert.assertNotNull("Lines object.", lines);
        Assert.assertEquals("Lines quantity.", 8, lines.length);
        Assert.assertEquals("Line no. 1.", "@keyframes rotate {", lines[0]);
        Assert.assertEquals("Line no. 2.", "from  {", lines[1]);
        Assert.assertEquals("Line no. 3.", "  transform: rotate(0deg);", lines[2]);
        Assert.assertEquals("Line no. 4.", "}", lines[3]);
        Assert.assertEquals("Line no. 5.", "to {", lines[4]);
        Assert.assertEquals("Line no. 6.", "  transform: rotate(360deg);", lines[5]);
        Assert.assertEquals("Line no. 7.", "}", lines[6]);
        Assert.assertEquals("Line no. 8.", "}", lines[7]);
    }
}
