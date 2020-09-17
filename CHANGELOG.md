# CSS Splitter Maven Plugin Changelog

## 2.0.4
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.26 to 0.9.27 ([#32](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/32))

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/LATEST/)

## 2.0.3
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.25 to 0.9.26 ([#31](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/31))

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/2.0.3/)

## 2.0.2
Bugs:
* Fixed regex patterns should not be created needlessly ([#30](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/30))

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/2.0.2/)

## 2.0.1
Bugs:
* Fixed colors should be written as hex codes instead of rgb functions ([#28](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/28))

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/2.0.1/)

## 2.0.0
Features:
* Set compatibility with Java 6+
* Set compatibility with Maven 3+

Bugs:
* Fixed `!important` is missing in split code  ([#23](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/23))
* Fixed special character sequences are interpreted instead of copied as is ([#26](https://github.com/gabrysbiz/css-splitter-maven-plugin/issues/26))

Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.24 to 0.9.25
* Upgraded [Maven Plugin Utils](https://gabrysbiz.github.io/maven-plugin-utils) from [1.4.1](https://gabrysbiz.github.io/maven-plugin-utils1.4.1/) to [2.0.0](https://gabrysbiz.github.io/maven-plugin-utils2.0.0/)

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/2.0.0/)

## 1.2.3
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.23 to 0.9.24
* Upgraded [Maven Plugin Utils](https://gabrysbiz.github.io/maven-plugin-utils) from [1.4.0](https://gabrysbiz.github.io/maven-plugin-utils1.4.0/) to [1.4.1](https://gabrysbiz.github.io/maven-plugin-utils1.4.1/)

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.2.3/)

## 1.2.2
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.22 to 0.9.23

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.2.2/)

## 1.2.1
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.21 to 0.9.22

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.2.1/)

## 1.2.0
Features:
* Added support for nested `@media` rules in CSS 3.0
* Added support for "star hack" in CSS 2.1 and 3.0 (see the [starHackAllowed](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.2.0/split-mojo.html#starHackAllowed) parameter)

Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.20 to 0.9.21

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.2.0/)

## 1.1.1
Dependencies:
* Upgraded [Maven Plugin Utils](https://gabrysbiz.github.io/maven-plugin-utils) from [1.3.0](https://gabrysbiz.github.io/maven-plugin-utils1.3.0/) to [1.4.0](https://gabrysbiz.github.io/maven-plugin-utils1.4.0/)

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.1.1/)

## 1.1.0
Features:
* Added code compression (see the [compress](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.1.0/split-mojo.html#compress) parameter)

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.1.0/)

## 1.0.1
Dependencies:
* Upgraded [CSS Parser](http://cssparser.sourceforge.net/) from 0.9.19 to 0.9.20

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.0.1/)

## 1.0.0
Initial release.

[See documentation](https://gabrysbiz.github.io/css-splitter-maven-plugin/1.0.0/)