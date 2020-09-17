# About
[![License BSD 3-Clause](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg)](https://gabrysbiz.github.io/css-splitter-maven-plugin/license.txt)
[![Build Status](https://travis-ci.org/gabrysbiz/css-splitter-maven-plugin.svg?branch=master)](https://travis-ci.org/gabrysbiz/css-splitter-maven-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=biz.gabrys.maven.plugins%3Acss-splitter-maven-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=biz.gabrys.maven.plugins%3Acss-splitter-maven-plugin)

This plugin splits [CSS](http://www.w3.org/Style/CSS/) stylesheets to smaller files ("parts") which contain maximum [X](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/split-mojo.html#maxRules) rules. It uses [CSS Parser](http://cssparser.sourceforge.net/) to parse source code.

[CSS Parser](http://cssparser.sourceforge.net/) supports following standards:
* [Cascading Style Sheets Level 3](https://www.w3.org/Style/CSS/)
* [Cascading Style Sheets Level 2 Revision 1 (CSS 2.1) Specification](https://www.w3.org/TR/CSS2/)
* [Cascading Style Sheets Level 2](https://www.w3.org/TR/2008/REC-CSS2-20080411/)
* [Cascading Style Sheets Level 1](https://www.w3.org/TR/CSS1/)

For more information read the [split](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/split-mojo.html) goal documentation or take a look into [examples](#examples).

# Goals Overview
* [css-splitter:split](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/split-mojo.html) - splits [CSS](http://www.w3.org/Style/CSS/) stylesheets to smaller files ("parts") which contain maximum [X](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/split-mojo.html#maxRules) rules

# Requirements
The plugin to run requires:
* Java 6 or higher
* Maven 3 or higher

# Usage
General instructions on how to use the CSS Splitter Maven Plugin can be found on the [usage](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/usage.html) page. Some more specific use cases are described in the examples given below. Last but not least, users occasionally contribute additional examples, tips or errata to the plugin's [wiki](https://github.com/gabrysbiz/css-splitter-maven-plugin/wiki) page.

In case you still have questions regarding the plugin's usage, please have a look at the [FAQ](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/faq.html).

If you feel like the plugin is missing a feature or has a defect, you can fill a feature request or bug report in the [issue tracker](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/issue-tracking.html). When creating a new issue, please provide a comprehensive description of your concern. Especially for fixing bugs it is crucial that the developers can reproduce your problem. For this reason, entire debug logs, POMs or most preferably little demo projects attached to the issue are very much appreciated. Of course, patches are welcome, too. Contributors can check out the project from the [source repository](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/source-repository.html) and will find supplementary information in the [guide to helping with Maven](http://maven.apache.org/guides/development/guide-helping.html).

# Examples
To provide you with better understanding of some usages of the CSS Splitter Maven Plugin, you can take a look into the following examples:
* [Using include/exclude patterns](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/examples/patterns.html)
* [Multiple source/output directories](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/examples/multiple-directories.html)
* [Output file structure (imports' tree)](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/examples/output-file-structure.html)
* [Using cache token](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/examples/cache-token.html)
* [Code compression](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/examples/code-compression.html)

