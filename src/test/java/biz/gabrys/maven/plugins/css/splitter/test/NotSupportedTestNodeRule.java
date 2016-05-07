package biz.gabrys.maven.plugins.css.splitter.test;

import biz.gabrys.maven.plugins.css.splitter.css.types.AbstractNodeRule;

public class NotSupportedTestNodeRule extends AbstractNodeRule {

    @Override
    protected int countSize() {
        throw new UnsupportedOperationException();
    }
}
