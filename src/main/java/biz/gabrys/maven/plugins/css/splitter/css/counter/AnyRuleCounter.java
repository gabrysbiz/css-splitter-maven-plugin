/*
 * CSS Splitter Maven Plugin
 * http://css-splitter-maven-plugin.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.css.counter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public class AnyRuleCounter implements RuleCounter {

    private final List<RuleCounter> counters;

    public AnyRuleCounter() {
        this(Arrays.<RuleCounter>asList(new ComplexRuleCounter(), new StyleRuleCounter(), new UnknownRuleCounter()));
    }

    // for tests
    AnyRuleCounter(final List<RuleCounter> counters) {
        this.counters = new ArrayList<RuleCounter>(counters);
    }

    public boolean isSupportedType(final NodeRule rule) {
        return true;
    }

    public int count(final NodeRule rule) {
        for (final RuleCounter counter : counters) {
            if (counter.isSupportedType(rule)) {
                return counter.count(rule);
            }
        }
        return 0;
    }
}
