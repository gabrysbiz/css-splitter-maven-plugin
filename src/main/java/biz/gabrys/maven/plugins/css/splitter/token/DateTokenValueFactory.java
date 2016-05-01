/**
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
package biz.gabrys.maven.plugins.css.splitter.token;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class DateTokenValueFactory implements TokenValueFactory {

    public String create(final String value) {
        final DateFormat dateFormat = new SimpleDateFormat(value, Locale.ENGLISH);
        return dateFormat.format(getNow());
    }

    Date getNow() {
        return new Date();
    }
}
