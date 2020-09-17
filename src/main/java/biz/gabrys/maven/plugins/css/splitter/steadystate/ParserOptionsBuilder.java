/*
 * CSS Splitter Maven Plugin
 * https://gabrysbiz.github.io/css-splitter-maven-plugin/
 *
 * Copyright (c) 2015-2020 Adam Gabrys
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 * - a copy of the License at project page
 * - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.maven.plugins.css.splitter.steadystate;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

public class ParserOptionsBuilder {

    private ParserOptionsImpl options;

    public ParserOptionsBuilder withStandard(final Standard standard) {
        getOptions().standard = standard;
        return this;
    }

    public ParserOptionsBuilder withStrict(final boolean strict) {
        getOptions().strict = strict;
        return this;
    }

    public ParserOptionsBuilder withStarHack(final boolean allowed) {
        getOptions().starHackAllowed = allowed;
        return this;
    }

    public ParserOptions create() {
        final ParserOptions opts = getOptions();
        options = null;
        return opts;
    }

    private ParserOptionsImpl getOptions() {
        if (options == null) {
            options = new ParserOptionsImpl();
        }
        return options;
    }

    private static final class ParserOptionsImpl implements ParserOptions {

        private Standard standard;
        private boolean strict;
        private boolean starHackAllowed;

        @Override
        public Standard getStandard() {
            return standard;
        }

        @Override
        public boolean isStrict() {
            return strict;
        }

        @Override
        public boolean isStarHackAllowed() {
            return starHackAllowed;
        }
    }
}
