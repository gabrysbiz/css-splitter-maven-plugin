package biz.gabrys.maven.plugins.css.splitter.css.type;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(lines).hasSize(8);
        assertThat(lines[0]).isEqualTo("@keyframes rotate {");
        assertThat(lines[1]).isEqualTo("from  {");
        assertThat(lines[2]).isEqualTo("  transform: rotate(0deg);");
        assertThat(lines[3]).isEqualTo("}");
        assertThat(lines[4]).isEqualTo("to {");
        assertThat(lines[5]).isEqualTo("  transform: rotate(360deg);");
        assertThat(lines[6]).isEqualTo("}");
        assertThat(lines[7]).isEqualTo("}");
    }
}
