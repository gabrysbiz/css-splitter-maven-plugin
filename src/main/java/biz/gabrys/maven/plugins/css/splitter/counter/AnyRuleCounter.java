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
package biz.gabrys.maven.plugins.css.splitter.counter;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;

public class AnyRuleCounter {

    private final Map<Class<? extends NodeRule>, RuleCounter<?>> counters;

    public AnyRuleCounter() {
        this(Arrays.<RuleCounter<?>>asList(new ComplexRuleCounter(), new StyleRuleCounter(), new UnknownRuleCounter()));
    }

    AnyRuleCounter(final Iterable<RuleCounter<?>> counters) {
        this.counters = new ConcurrentHashMap<Class<? extends NodeRule>, RuleCounter<?>>();
        for (final RuleCounter<?> counter : counters) {
            this.counters.put(counter.getSupportedType(), counter);
        }
    }

    public int count(final NodeRule rule) {
        if (counters.containsKey(rule.getClass())) {
            return counters.get(rule.getClass()).count(rule);
        }
        return 0;
    }
}
