package biz.gabrys.maven.plugins.css.splitter.validation;

import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;

// TODO add tests
class ComplexRulePropertiesLimitValidator extends AbstractRulePropertiesLimitValidator<ComplexRule> {

    private final StyleRulePropertiesLimitValidator validator;

    ComplexRulePropertiesLimitValidator() {
        super(ComplexRule.class);
        validator = new StyleRulePropertiesLimitValidator();
    }

    @Override
    protected void validate2(final ComplexRule rule, final int limit) throws ValidationException {
        for (final StyleRule child : rule.getRules()) {
            validator.validate2(child, limit);
        }
    }
}
