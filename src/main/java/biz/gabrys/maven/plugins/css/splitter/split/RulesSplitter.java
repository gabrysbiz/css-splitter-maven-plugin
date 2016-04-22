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

import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.counter.RuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

class RulesSplitter<T extends NodeRule> {

    private final RuleCounter counter;
    private final RuleSplitter<T> splitter;

    RulesSplitter(final RuleCounter counter, final RuleSplitter<T> splitter) {
        this.counter = counter;
        this.splitter = splitter;
    }

    RulesContainer<T> split(final List<T> rules, final int splitAfter) {
        final RulesContainer<T> container = new RulesContainer<T>();
        final ValueAndIndex info = new ValueAndIndex(splitAfter);
        processBeforeSplitPoint(rules, container, info);
        if (info.value != 0) {
            processSplitPoint(rules, container, info);
        }
        processAfterSplitPoint(rules, container, info);
        return container;
    }

    private void processBeforeSplitPoint(final List<T> rules, final RulesContainer<T> container, final ValueAndIndex info) {
        while (info.index < rules.size()) {
            final T rule = rules.get(info.index);
            final int count = counter.count(rule);
            final int odds = info.value - count;
            if (odds < 0) {
                return;
            }
            container.before.add(rule);
            info.value = odds;
            ++info.index;
        }
        info.value = 0;
    }

    private void processSplitPoint(final List<T> rules, final RulesContainer<T> container, final ValueAndIndex info) {
        final T rule = rules.get(info.index);
        ++info.index;
        if (splitter.isSplittable(rule)) {
            final SplitResult<? extends T> result = splitter.split(rule, info.value);
            container.before.add(result.getBefore());
            container.after.add(result.getAfter());
            return;
        }
        container.after.add(rule);
    }

    private static <T extends NodeRule> void processAfterSplitPoint(final List<T> rules, final RulesContainer<T> container,
            final ValueAndIndex info) {
        if (info.index < rules.size()) {
            container.after.addAll(rules.subList(info.index, rules.size()));
        }
    }

    private static class ValueAndIndex {

        private int value;
        private int index;

        ValueAndIndex(final int value) {
            this.value = value;
        }
    }
}
