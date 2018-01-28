# About
[![License BSD 3-Clause](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg)](http://css-splitter-maven-plugin.projects.gabrys.biz/license.txt)
[![Build Status](https://travis-ci.org/gabrysbiz/css-splitter-maven-plugin.svg?branch=release%2F2.0.0)](https://travis-ci.org/gabrysbiz/css-splitter-maven-plugin)

This plugin splits [CSS](http://www.w3.org/Style/CSS/) stylesheets to smaller files ("parts") which contain maximum [X](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/split-mojo.html#maxRules) rules. It uses [CSS Parser](http://cssparser.sourceforge.net/) to parse source code.

[CSS Parser](http://cssparser.sourceforge.net/) supports following standards:
* [Cascading Style Sheets Level 3](https://www.w3.org/Style/CSS/)
* [Cascading Style Sheets Level 2 Revision 1 (CSS 2.1) Specification](https://www.w3.org/TR/CSS2/)
* [Cascading Style Sheets Level 2](https://www.w3.org/TR/2008/REC-CSS2-20080411/)
* [Cascading Style Sheets Level 1](https://www.w3.org/TR/CSS1/)

For more information read the [split](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/split-mojo.html) goal documentation or take a look into [examples](#examples).

# Goals Overview
* [css-splitter:split](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/split-mojo.html) - splits [CSS](http://www.w3.org/Style/CSS/) stylesheets to smaller files ("parts") which contain maximum [X](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/split-mojo.html#maxRules) rules

# Requirements
The plugin to run requires:
* Java 6 or higher
* Maven 3 or higher

# Usage
General instructions on how to use the CSS Splitter Maven Plugin can be found on the [usage](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/usage.html) page. Some more specific use cases are described in the examples given below. Last but not least, users occasionally contribute additional examples, tips or errata to the plugin's [wiki](https://github.com/gabrysbiz/css-splitter-maven-plugin/wiki) page.

In case you still have questions regarding the plugin's usage, please have a look at the [FAQ](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/faq.html).

If you feel like the plugin is missing a feature or has a defect, you can fill a feature request or bug report in the [issue tracker](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/issue-tracking.html). When creating a new issue, please provide a comprehensive description of your concern. Especially for fixing bugs it is crucial that the developers can reproduce your problem. For this reason, entire debug logs, POMs or most preferably little demo projects attached to the issue are very much appreciated. Of course, patches are welcome, too. Contributors can check out the project from the [source repository](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/source-repository.html) and will find supplementary information in the [guide to helping with Maven](http://maven.apache.org/guides/development/guide-helping.html).

# Examples
To provide you with better understanding of some usages of the CSS Splitter Maven Plugin, you can take a look into the following examples:
* [Using include/exclude patterns](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/examples/patterns.html)
* [Multiple source/output directories](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/examples/multiple-directories.html)
* [Output file structure (imports' tree)](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/examples/output-file-structure.html)
* [Using cache token](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/examples/cache-token.html)
* [Code compression](http://css-splitter-maven-plugin.projects.gabrys.biz/2.0.0/examples/code-compression.html)

