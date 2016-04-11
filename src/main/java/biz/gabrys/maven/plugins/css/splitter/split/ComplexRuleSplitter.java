/*
 * CSS Splitter Maven Plugin
 * http://www.gabrys.biz/projects/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *      https://raw.githubusercontent.com/gabrysbiz/css-splitter-maven-plugin/master/src/main/resources/license.txt
 */
package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.LinkedList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.counter.StyleRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.ComplexRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.TreeSplitUtils;

//TODO add tests
class ComplexRuleSplitter extends AbstractRuleSplitter<ComplexRule> {

    private final StyleRuleCounter counter;
    private final StyleRuleSplitter splitter;

    ComplexRuleSplitter() {
        super(ComplexRule.class);
        counter = new StyleRuleCounter();
        splitter = new StyleRuleSplitter();
    }

    @Override
    protected SplitResult<ComplexRule> split2(final ComplexRule rule, final int splitAfter) {
        final RulesContainer container = getRules(rule.getRules(), splitAfter);
        final ComplexRule first = new ComplexRule(rule.getType(), rule.getSelectors(), container.first);
        final ComplexRule second = new ComplexRule(rule.getType(), rule.getSelectors(), container.second);
        TreeSplitUtils.fillNeighbors(rule, first, second);
        return new SplitResult<ComplexRule>(first, second);
    }

    private RulesContainer getRules(final List<StyleRule> rules, final int splitAfter) {
        final RulesContainer container = new RulesContainer();
        int value = splitAfter;
        for (int i = 0; i < rules.size(); ++i) {
            final StyleRule styleRule = rules.get(i);
            final int count = counter.count(styleRule);
            final int odds = value - count;
            if (odds > 0) {
                container.first.add(styleRule);
                value = odds;
                continue;
            }

            if (odds == 0) {
                container.first.add(styleRule);
            } else {
                final SplitResult<StyleRule> result = splitter.split2(styleRule, value);
                container.first.add(result.getFirst());
                container.second.add(result.getSecond());
            }
            container.second.addAll(rules.subList(i, rules.size()));
            break;
        }
        return container;
    }

    private static class RulesContainer {

        private final List<StyleRule> first = new LinkedList<StyleRule>();
        private final List<StyleRule> second = new LinkedList<StyleRule>();
    }
}
