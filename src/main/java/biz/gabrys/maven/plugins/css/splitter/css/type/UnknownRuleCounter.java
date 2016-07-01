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
package biz.gabrys.maven.plugins.css.splitter.css.type;

class UnknownRuleCounter {

    int count(final UnknownRule rule) {
        final String code = rule.getCode();
        if (code.endsWith("}")) {
            return countNestedRule(code);
        }
        return countSimpleRule(code);
    }

    private static int countSimpleRule(final String code) {
        final String[] parts = code.split(";");
        final int value = parts.length;
        final String last = parts[value - 1];
        for (int i = 0; i < last.length(); ++i) {
            if (!Character.isWhitespace(last.charAt(i))) {
                return value;
            }
        }
        return value - 1;
    }

    private static int countNestedRule(final String code) {
        String tmp = code;
        if (!tmp.endsWith(";") && !tmp.endsWith("}")) {
            tmp += String.valueOf(';');
        }
        final String[] parts = tmp.split("}");
        int value = 0;
        for (final String part : parts) {
            value += countNestedPart(part.trim());
        }
        return value;
    }

    private static int countNestedPart(final String part) {
        String tmp = part.trim();
        if (!tmp.endsWith(";")) {
            tmp += String.valueOf(';');
        }
        return tmp.split(";").length;
    }
}
