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
package biz.gabrys.maven.plugins.css.splitter.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import biz.gabrys.maven.plugins.css.splitter.css.type.NodeRule;
import biz.gabrys.maven.plugins.css.splitter.css.type.StyleSheet;

public class StyleSheetSplliter {

    private final int limit;
    private final RulesSplitter splitter;

    public StyleSheetSplliter(final int limit) {
        this.limit = limit;

        final List<RuleSplitter> splitters = new ArrayList<RuleSplitter>();
        splitters.add(new StyleRuleSplitter());
        splitters.add(new ComplexRuleSplitter());
        splitter = new RulesSplitter(new MultipleRuleSplitter(splitters));
    }

    // for tests
    StyleSheetSplliter(final int limit, final RulesSplitter splitter) {
        this.limit = limit;
        this.splitter = splitter;
    }

    public List<StyleSheet> split(final StyleSheet stylesheet) {
        if (stylesheet.getRules().isEmpty()) {
            return Arrays.asList(stylesheet);
        }
        final List<List<NodeRule>> rulesMatrix = splitToMatrix(stylesheet);
        return convertToSheets(rulesMatrix);
    }

    private List<List<NodeRule>> splitToMatrix(final StyleSheet stylesheet) {
        final List<List<NodeRule>> rulesMatrix = new LinkedList<List<NodeRule>>();
        List<NodeRule> currentRules = stylesheet.getRules();
        while (!currentRules.isEmpty()) {
            final RulesContainer container = splitter.split(currentRules, limit);
            rulesMatrix.add(container.before);
            currentRules = container.after;
        }
        return rulesMatrix;
    }

    private static List<StyleSheet> convertToSheets(final List<List<NodeRule>> rulesMatrix) {
        final List<StyleSheet> sheets = new ArrayList<StyleSheet>(rulesMatrix.size());
        for (final List<NodeRule> sheetRules : rulesMatrix) {
            sheets.add(new StyleSheet(sheetRules));
        }
        return sheets;
    }
}
