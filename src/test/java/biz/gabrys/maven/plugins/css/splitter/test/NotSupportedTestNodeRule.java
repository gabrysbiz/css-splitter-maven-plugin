package biz.gabrys.maven.plugins.css.splitter.test;

import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.AbstractNodeRule;

public class NotSupportedTestNodeRule extends AbstractNodeRule {

    @Override
    protected void fillLines(final List<String> lines) {
        lines.add(getClass().getName());
    }

    @Override
    protected int size2() {
        throw new UnsupportedOperationException();
    }
}
