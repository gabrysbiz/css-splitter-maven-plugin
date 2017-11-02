package biz.gabrys.maven.plugins.css.splitter.compressor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class CodeCompressorTest {

    @Test
    public void compress_negativeLineBreak_compressedSuccessfully() {
        final StringBuilder css = new StringBuilder();
        css.append(".class {");
        css.append("  border-style: solid;");
        css.append("  border-width: 0px;");
        css.append("  border-color: #999999;");
        css.append("}");
        css.append("div {");
        css.append("  width: 0px;");
        css.append("}");

        final CodeCompressor compressor = new CodeCompressor(-1);
        final String compressed = compressor.compress(css.toString());

        assertEquals(".class{border-style:solid;border-width:0;border-color:#999}div{width:0}", compressed);
    }

    @Test
    public void compress_lineBreakAfter10Characters_compressedSuccessfully() {
        final StringBuilder css = new StringBuilder();
        css.append(".class {");
        css.append("  width: 10px;");
        css.append("  height: 10px;");
        css.append("}");
        css.append("div {");
        css.append("  color: red;");
        css.append("}");

        final CodeCompressor compressor = new CodeCompressor(10);
        final String compressed = compressor.compress(css.toString());

        assertEquals(".class{width:10px;height:10px}\ndiv{color:red}", compressed);
    }
}
