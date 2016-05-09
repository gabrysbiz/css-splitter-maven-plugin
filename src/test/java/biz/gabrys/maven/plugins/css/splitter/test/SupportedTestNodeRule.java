package biz.gabrys.maven.plugins.css.splitter.test;

import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.AbstractNodeRule;

public class SupportedTestNodeRule extends AbstractNodeRule {

    @Override
    protected void fillLines(final List<String> lines) {
        lines.add(getClass().getName());
    }

    @Override
    protected int getSize2() {
        throw new UnsupportedOperationException();
    }
}
