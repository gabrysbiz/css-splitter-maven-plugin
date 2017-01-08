package biz.gabrys.maven.plugins.css.splitter.steadystate;

import org.junit.Ignore;

import biz.gabrys.maven.plugins.css.splitter.css.Standard;

// TODO: does not work with CSS 1.0 and 2.0 -> https://sourceforge.net/p/cssparser/bugs/62/
@Ignore
public final class Css10StarHackTest extends AbstractStarHackTest {

    public Css10StarHackTest() {
        super(Standard.VERSION_1_0);
    }
}
