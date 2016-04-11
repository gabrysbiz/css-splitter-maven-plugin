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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import biz.gabrys.maven.plugins.css.splitter.counter.AnyRuleCounter;
import biz.gabrys.maven.plugins.css.splitter.css.types.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;

//TODO add tests
public class Splliter {

    private final int limit;
    private final Map<Class<?>, RuleSplitter<? extends NodeRule>> splitters = new ConcurrentHashMap<Class<?>, RuleSplitter<? extends NodeRule>>();
    private final AnyRuleCounter counter;

    public Splliter(final int limit) {
        this.limit = limit;
        counter = new AnyRuleCounter();
        addSplitter(new ComplexRuleSplitter());
        addSplitter(new StyleRuleSplitter());
        addSplitter(new UnknownRuleSplitter());
    }

    private void addSplitter(final RuleSplitter<? extends NodeRule> splitter) {
        splitters.put(splitter.getSupportedType(), splitter);
    }

    public List<StyleSheet> split(final StyleSheet stylesheet) {
        if (stylesheet.getRules().isEmpty()) {
            return Arrays.asList(stylesheet);
        }
        final List<List<NodeRule>> rulesMatrix = splitToMatrix(stylesheet.getRules());
        return convertToSheets(rulesMatrix);
    }

    private List<List<NodeRule>> splitToMatrix(final List<NodeRule> rules) {
        final List<List<NodeRule>> rulesMatrix = new LinkedList<List<NodeRule>>();

        int value = limit;
        List<NodeRule> currentRules = null;
        NodeRule rule = rules.get(0);
        while (rule != null) {
            if (currentRules == null) {
                currentRules = new LinkedList<NodeRule>();
                rulesMatrix.add(currentRules);
            }

            final int count = counter.count(rule);
            final int odds = value - count;
            if (odds > 0) {
                currentRules.add(rule);
                value = odds;
                rule = rule.getNext();
                continue;
            }

            if (odds == 0) {
                currentRules.add(rule);
                rule = rule.getNext();
            } else {
                final SplitResult<? extends NodeRule> result = splitters.get(rule.getClass()).split(rule, value);
                currentRules.add(result.getFirst());
                rule = result.getSecond();
            }
            value = limit;
            currentRules = null;
        }
        return rulesMatrix;
    }

    private List<StyleSheet> convertToSheets(final List<List<NodeRule>> rulesMatrix) {
        final List<StyleSheet> sheets = new ArrayList<StyleSheet>(rulesMatrix.size());
        for (final List<NodeRule> sheetRules : rulesMatrix) {
            sheets.add(new StyleSheet(sheetRules));
        }
        return sheets;
    }
}
