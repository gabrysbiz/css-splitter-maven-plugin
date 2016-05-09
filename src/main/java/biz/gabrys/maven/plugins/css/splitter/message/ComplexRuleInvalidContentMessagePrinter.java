package biz.gabrys.maven.plugins.css.splitter.message;

import org.apache.maven.plugin.logging.Log;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

public class ComplexRuleInvalidContentMessagePrinter extends AbstractMessagePrinter<ComplexRule> {

    private final Log logger;
    private final boolean strict;

    public ComplexRuleInvalidContentMessagePrinter(final Log logger, final boolean strict) {
        super(ComplexRule.class);
        this.logger = logger;
        this.strict = strict;
    }

    public boolean isEnabled() {
        return !strict;
    }

    @Override
    protected void print2(final ComplexRule rule) {
        for (final NodeRule child : rule.getRules()) {
            if (!(child instanceof StyleRule)) {
                logger.warn(String.format("NON-STRICT: found non-standard content in %s rule!", rule.getType()));
                logger.warn(String.format("NON-STRICT: non-standard content:%n%s", child.getCode()));
                logger.warn(String.format("NON-STRICT: %s rule code:%n%s", rule.getType(), rule.getCode()));
            }
        }
    }
}
