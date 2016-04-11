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

import biz.gabrys.maven.plugins.css.splitter.css.types.UnknownRule;

//TODO add tests
class UnknownRuleSplitter extends AbstractRuleSplitter<UnknownRule> {

    UnknownRuleSplitter() {
        super(UnknownRule.class);
    }

    @Override
    protected SplitResult<UnknownRule> split2(final UnknownRule rule, final int splitAfter) {
        return new SplitResult<UnknownRule>(null, rule);
    }
}
