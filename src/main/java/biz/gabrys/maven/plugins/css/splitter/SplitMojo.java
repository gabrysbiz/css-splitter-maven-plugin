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
package biz.gabrys.maven.plugins.css.splitter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import biz.gabrys.maven.plugin.util.io.DestinationFileCreator;
import biz.gabrys.maven.plugin.util.io.FileScanner;
import biz.gabrys.maven.plugin.util.io.ScannerFactory;
import biz.gabrys.maven.plugin.util.io.ScannerPatternFormat;
import biz.gabrys.maven.plugin.util.timer.SystemTimer;
import biz.gabrys.maven.plugin.util.timer.Timer;
import biz.gabrys.maven.plugins.css.splitter.counter.LoggingStyleSheetCounter;
import biz.gabrys.maven.plugins.css.splitter.counter.StyleSheetCounter;
import biz.gabrys.maven.plugins.css.splitter.counter.StyleSheetCounterImpl;
import biz.gabrys.maven.plugins.css.splitter.css.Standard;
import biz.gabrys.maven.plugins.css.splitter.css.types.StyleSheet;
import biz.gabrys.maven.plugins.css.splitter.net.UrlEscaper;
import biz.gabrys.maven.plugins.css.splitter.split.Splliter;
import biz.gabrys.maven.plugins.css.splitter.steadystate.SteadyStateParser;
import biz.gabrys.maven.plugins.css.splitter.token.TokenType;
import biz.gabrys.maven.plugins.css.splitter.tree.OrderedTree;
import biz.gabrys.maven.plugins.css.splitter.tree.TreeNode;
import biz.gabrys.maven.plugins.css.splitter.validation.RulesLimitValidator;
import biz.gabrys.maven.plugins.css.splitter.validation.StylePropertiesLimitValidator;
import biz.gabrys.maven.plugins.css.splitter.validation.StyleSheetValidator;
import biz.gabrys.maven.plugins.css.splitter.validation.ValidationException;

/**
 * Splits <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets to smaller files (parts).
 * @since 1.0
 */
@Mojo(name = "split", defaultPhase = LifecyclePhase.PROCESS_SOURCES, threadSafe = true)
public class SplitMojo extends AbstractMojo {

    private static final String PART_INDEX_PARAMETER = "{index}";

    /**
     * Defines whether to skip the plugin execution.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.skip", defaultValue = "false")
    protected boolean skip;

    /**
     * Defines whether the plugin runs in verbose mode.<br>
     * <b>Notice</b>: always true in debug mode.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.verbose", defaultValue = "false")
    protected boolean verbose;

    /**
     * Forces to always split the <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets. By default sources are
     * only split when modified or the main destination files does not exist.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.force", defaultValue = "false")
    protected boolean force;

    /**
     * The directory which contains the <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.sourceDirectory", defaultValue = "${project.basedir}/src/main/css")
    protected File sourceDirectory;

    /**
     * Specifies where to place split <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.outputDirectory", defaultValue = "${project.build.directory}")
    protected File outputDirectory;

    /**
     * Defines inclusion and exclusion fileset patterns format. Available options:
     * <ul>
     * <li><b>ant</b> - <a href="http://ant.apache.org/">Ant</a>
     * <a href="http://ant.apache.org/manual/dirtasks.html#patterns">patterns</a></li>
     * <li><b>regex</b> - regular expressions (use '/' as path separator)</li>
     * </ul>
     * @since 1.0
     */
    @Parameter(property = "css.splitter.filesetPatternFormat", defaultValue = "ant")
    protected String filesetPatternFormat;

    /**
     * List of files to include. Specified as fileset patterns which are relative to the
     * <a href="#sourceDirectory">source directory</a>. See <a href="#filesetPatternFormat">available fileset patterns
     * formats</a>.<br>
     * <b>Default value is</b>: <tt>["&#42;&#42;/&#42;.css"]</tt> for <a href="#filesetPatternFormat">ant</a> or
     * <tt>["^.+\.css$"]</tt> for <a href="#filesetPatternFormat">regex</a>.
     * @since 1.0
     */
    @Parameter
    protected String[] includes = new String[0];

    /**
     * List of files to exclude. Specified as fileset patterns which are relative to the
     * <a href="#sourceDirectory">source directory</a>. See <a href="#filesetPatternFormat">available fileset patterns
     * formats</a>.<br>
     * <b>Default value is</b>: <tt>[]</tt>.
     * @since 1.0
     */
    @Parameter
    protected String[] excludes = new String[0];

    /**
     * The maximum number of <a href="http://www.w3.org/Style/CSS/">CSS</a> &quot;style rules&quot; in destination
     * files. &quot;Style rule&quot; is a rule that contains properties. Examples:
     * 
     * <pre>
     * /&#42; count as 2 &#42;/
     * &#64;font-face {
     *      font-family: FontFamilyName;
     *      src: url(&quot;font.woff2&quot;) format(&quot;woff2&quot;), url(&quot;font.ttf&quot;) format(&quot;truetype&quot;);
     * }
     * /&#42; count as 4 &#42;/
     * .element {
     *      width: 100px;
     *      height: 200px;
     *      margin: 0;
     *      padding: 0;
     * }
     * /&#42; count as 1 (for safety) &#42;/
     * .empty {
     * }
     * /&#42; count as 0 &#42;/
     * &#64;media screen and (min-width: 480px) {
     * }
     * /&#42; summary: count as 2 (but can be split) &#42;/
     * &#64;media screen and (min-width: 480px) {
     *     /&#42; count as 1 &#42;/
     *     rule {
     *          width: 100px;
     *     }
     *     /&#42; count as 1 (for safety) &#42;/
     *     .empty {
     *     }
     * }
     * </pre>
     * 
     * <b>Notice</b>: all values smaller than <tt>1</tt> are treated as <tt>2147483647</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.maxRules", defaultValue = "4095")
    protected int maxRules;

    /**
     * The plugin failures build when a number of <a href="http://www.w3.org/Style/CSS/">CSS</a> &quot;style rules&quot;
     * in source files exceeds this value. &quot;Style rule&quot; is a rule that contains properties. Examples:
     * 
     * <pre>
     * /&#42; count as 2 &#42;/
     * &#64;font-face {
     *      font-family: FontFamilyName;
     *      src: url(&quot;font.woff2&quot;) format(&quot;woff2&quot;), url(&quot;font.ttf&quot;) format(&quot;truetype&quot;);
     * }
     * /&#42; count as 4 &#42;/
     * .element {
     *      width: 100px;
     *      height: 200px;
     *      margin: 0;
     *      padding: 0;
     * }
     * /&#42; count as 1 (for safety) &#42;/
     * .empty {
     * }
     * /&#42; count as 0 &#42;/
     * &#64;media screen and (min-width: 480px) {
     * }
     * /&#42; summary: count as 2 (but can be split) &#42;/
     * &#64;media screen and (min-width: 480px) {
     *     /&#42; count as 1 &#42;/
     *     rule {
     *          width: 100px;
     *     }
     *     /&#42; count as 1 (for safety) &#42;/
     *     .empty {
     *     }
     * }
     * </pre>
     * 
     * <b>Notice</b>: all values smaller than <tt>1</tt> are treated as <tt>2147483647</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.rulesLimit", defaultValue = "2147483647")
    protected int rulesLimit;

    /**
     * The maximum number of generated <code>&#64;import</code> in a single file. The plugin does not check whether the
     * code in parts fulfills this condition.<br>
     * <b>Notice</b>: all values smaller than <tt>2</tt> are treated as <tt>2147483647</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.maxImports", defaultValue = "31")
    protected int maxImports;

    /**
     * The plugin failures build when a number of nesting <code>&#64;import</code> exceed this value. The plugin does
     * not check the code in parts.<br>
     * <b>Notice</b>: all values smaller than <tt>1</tt> are treated as <tt>2147483647</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.importsNestingLimit", defaultValue = "4")
    protected int importsNestingLimit;

    /**
     * The <a href="http://www.w3.org/Style/CSS/">CSS</a> standard used to parse source code. Available values:
     * <ul>
     * <li><b>3.0</b> - <a href="https://www.w3.org/Style/CSS/">Cascading Style Sheets Level 3</a></li>
     * <li><b>2.1</b> - <a href="https://www.w3.org/TR/CSS2/">Cascading Style Sheets Level 2 Revision 1 (CSS 2.1)
     * Specification</a></li>
     * <li><b>2.0</b> - <a href="https://www.w3.org/TR/2008/REC-CSS2-20080411/">Cascading Style Sheets Level 2</a></li>
     * <li><b>1.0</b> - <a href="https://www.w3.org/TR/CSS1/">Cascading Style Sheets, level 1</a></li>
     * </ul>
     * @since 1.0
     */
    @Parameter(property = "css.splitter.standard", defaultValue = "3.0")
    protected String standard;

    /**
     * Defines cache token type which will be added to <code>&#64;import</code> urls in destination
     * <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets. Available options:
     * <ul>
     * <li><b>custom</b> - text specified by the user</li>
     * <li><b>date</b> - build date</li>
     * <li><b>none</b> - token will not be added</li>
     * </ul>
     * @since 1.0
     */
    @Parameter(property = "css.splitter.cacheTokenType", defaultValue = "none")
    protected String cacheTokenType;

    /**
     * Defines cache token parameter name which will be added to <code>&#64;import</code> urls in destination
     * <a href="http://www.w3.org/Style/CSS/">CSS</a> stylesheets.<br>
     * <b>Notice</b>: ignored when <a href="#cacheTokenType">cache token type</a> is equal to <tt>none</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.cacheTokenParameter", defaultValue = "v")
    protected String cacheTokenParameter;

    /**
     * Stores different values depending on the <a href="#cacheTokenType">cache token type</a>:
     * <ul>
     * <li><b>custom</b> - user specified value (e.g. <code>constantText</code>, <code>${variable}</code>)</li>
     * <li><b>date</b> - pattern for {@link java.text.SimpleDateFormat} object</li>
     * <li><b>none</b> - ignored</li>
     * </ul>
     * <b>Default value is</b>: <tt>yyyyMMddHHmmss</tt> if <a href="#cacheTokenType">cache token type</a> is equal to
     * <tt>date</tt>.<br>
     * <b>Required</b>: <tt>YES</tt> if <a href="#cacheTokenType">cache token type</a> is equal to <tt>custom</tt>.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.cacheTokenValue")
    protected String cacheTokenValue;

    /**
     * Sources encoding.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.encoding", defaultValue = "${project.build.sourceEncoding}")
    protected String encoding;

    /**
     * Destination files naming pattern. {fileName} is equal to source file name without extension.
     * @since 1.0
     */
    @Parameter(property = "css.splitter.outputFileNamePattern", defaultValue = DestinationFileCreator.FILE_NAME_PARAMETER + ".css")
    protected String outputFileNamePattern;

    /**
     * Destination parts naming pattern. {fileName} is equal to source file name without extension, {index} is equal to
     * part index (first index is equal to 1).
     * @since 1.0
     */
    @Parameter(property = "css.splitter.outputPartNamePattern", defaultValue = DestinationFileCreator.FILE_NAME_PARAMETER + '-'
            + PART_INDEX_PARAMETER + ".css")
    protected String outputPartNamePattern;

    private void logParameters() {
        if (getLog().isDebugEnabled()) {
            getLog().debug("Job parameters:");
            getLog().debug("\tskip = " + skip);
            getLog().debug("\tverbose = " + verbose + createCalculatedInfo(Boolean.TRUE));
            getLog().debug("\tforce = " + force);
            getLog().debug("\tsourceDirectory = " + sourceDirectory);
            getLog().debug("\toutputDirectory = " + outputDirectory);
            getLog().debug("\tfilesetPatternFormat = " + filesetPatternFormat);
            final String calculatedIncludes = includes.length != 0 ? "" : createCalculatedInfo(Arrays.toString(getDefaultIncludes()));
            getLog().debug("\tincludes = " + Arrays.toString(includes) + calculatedIncludes);
            getLog().debug("\texcludes = " + Arrays.toString(excludes));
            final String calculatedIntegerMaxValue = createCalculatedInfo(Integer.MAX_VALUE);
            getLog().debug("\tmaxRules = " + maxRules + (maxRules > 0 ? "" : calculatedIntegerMaxValue));
            getLog().debug("\trulesLimit = " + rulesLimit + (rulesLimit > 0 ? "" : calculatedIntegerMaxValue));
            getLog().debug("\tmaxImports = " + maxImports + (maxImports > 1 ? "" : calculatedIntegerMaxValue));
            getLog().debug("\timportsNestingLimit = " + importsNestingLimit + (importsNestingLimit > 0 ? "" : calculatedIntegerMaxValue));
            getLog().debug("\tstandard = " + standard);
            getLog().debug("\tcacheTokenType = " + cacheTokenType);
            getLog().debug("\tcacheTokenParameter = " + cacheTokenParameter);
            String calculatedCacheTokenValue = "";
            if (cacheTokenValue == null) {
                final String defaultCacheTokenValue = getDefaultCacheTokenValue();
                calculatedCacheTokenValue = defaultCacheTokenValue != null ? createCalculatedInfo(defaultCacheTokenValue) : "";
            }
            getLog().debug("\tcacheTokenValue = " + cacheTokenValue + calculatedCacheTokenValue);
            getLog().debug("\tencoding = " + encoding);
            getLog().debug("\toutputFileNamePattern = " + outputFileNamePattern);
            getLog().debug("\toutputPartNamePattern = " + outputPartNamePattern);
            getLog().debug("");
        }
    }

    private static String createCalculatedInfo(final Object value) {
        return String.format(" (calculated: %s)", value);
    }

    private String[] getDefaultIncludes() {
        if (ScannerPatternFormat.ANT.name().equalsIgnoreCase(filesetPatternFormat)) {
            return new String[] { "**/*.css" };
        } else {
            return new String[] { "^.+\\.css$" };
        }
    }

    private String getDefaultCacheTokenValue() {
        if (TokenType.DATE.name().equalsIgnoreCase(cacheTokenType)) {
            return "yyyyMMddHHmmss";
        } else {
            return null;
        }
    }

    private void calculateParameters() {
        if (getLog().isDebugEnabled()) {
            verbose = true;
        }
        if (includes.length == 0) {
            includes = getDefaultIncludes();
        }
        if (maxRules < 1) {
            maxRules = Integer.MAX_VALUE;
        }
        if (rulesLimit < 1) {
            rulesLimit = Integer.MAX_VALUE;
        }
        if (maxImports < OrderedTree.MIN_NUMBER_OF_CHILDREN) {
            maxImports = Integer.MAX_VALUE;
        }
        if (importsNestingLimit < 1) {
            importsNestingLimit = Integer.MAX_VALUE;
        }
        if (cacheTokenValue == null) {
            cacheTokenValue = getDefaultCacheTokenValue();
        }
    }

    private void validateParameters() throws MojoExecutionException {
        if (cacheTokenValue == null && TokenType.CUSTOM.name().equalsIgnoreCase(cacheTokenType)) {
            throw new MojoExecutionException("Parameter cacheTokenValue is required when cacheTokenType is equal to \"custom\"!");
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        logParameters();
        if (skip) {
            getLog().info("Skipping job execution");
            return;
        }
        calculateParameters();
        validateParameters();
        runSplitter();
    }

    private void runSplitter() throws MojoFailureException {
        if (!sourceDirectory.exists()) {
            getLog().warn("Source directory does not exist: " + sourceDirectory.getAbsolutePath());
            return;
        }
        final Collection<File> files = getFiles();
        if (files.isEmpty()) {
            getLog().warn("No sources to compile");
            return;
        }
        splitFiles(files);
    }

    private Collection<File> getFiles() {
        final ScannerPatternFormat patternFormat = ScannerPatternFormat.toPattern(filesetPatternFormat);
        final FileScanner scanner = new ScannerFactory().create(patternFormat, getLog());
        if (verbose) {
            getLog().info("Scanning directory for sources...");
        }
        return scanner.getFiles(sourceDirectory, includes, excludes);
    }

    private void splitFiles(final Collection<File> sources) throws MojoFailureException {
        final String sourceFilesText = "source file" + (sources.size() != 1 ? "s" : "");
        getLog().info("Splitting " + sources.size() + ' ' + sourceFilesText + " to " + outputDirectory.getAbsolutePath());
        final Timer timer = SystemTimer.getStartedTimer();
        for (final File source : sources) {
            if (isCompilationRequired(source)) {
                splitFile(source);
            } else if (verbose) {
                getLog().info("Skipping stylesheet split (not modified): " + source.getAbsolutePath());
            }
        }
        getLog().info("Finished " + sourceFilesText + " split in " + timer.stop());
    }

    private boolean isCompilationRequired(final File source) {
        if (force) {
            return true;
        }
        final File destination = new DestinationFileCreator(sourceDirectory, outputDirectory, outputFileNamePattern).create(source);
        if (!destination.exists()) {
            return true;
        }
        return source.lastModified() > destination.lastModified();
    }

    private void splitFile(final File source) throws MojoFailureException {
        Timer timer = null;
        if (verbose) {
            getLog().info("Splitting stylesheet: " + source.getAbsolutePath());
            timer = SystemTimer.getStartedTimer();
        }
        final String css = readCss(source);
        final List<StyleSheet> parts;
        try {
            if (verbose) {
                getLog().info("Parsing stylesheet...");
            }
            final StyleSheet stylesheet = new SteadyStateParser(getLog()).parse(css, Standard.create(standard));
            validateStyleSheet(stylesheet);
            if (verbose) {
                getLog().info("Splitting stylesheet to parts...");
            }
            parts = new Splliter(maxRules).split(stylesheet);
        } catch (final Exception e) {
            throw new MojoFailureException(e.getMessage(), e);
        }
        if (verbose) {
            getLog().info(String.format("Split to %d stylesheet%s.", parts.size(), parts.size() == 1 ? "" : "s"));
        }
        saveParts(source, parts);
        if (timer != null) {
            getLog().info("Finished in " + timer.stop());
        }
    }

    private String readCss(final File file) throws MojoFailureException {
        try {
            return FileUtils.readFileToString(file, encoding);
        } catch (final IOException e) {
            throw new MojoFailureException("Cannot read file: " + file.getAbsolutePath(), e);
        }
    }

    private void validateStyleSheet(final StyleSheet stylesheet) throws ValidationException {
        if (verbose) {
            getLog().info("Validating stylesheet...");
        }

        final Collection<StyleSheetValidator> validators = new ArrayList<StyleSheetValidator>();
        StyleSheetCounter counter = new StyleSheetCounterImpl();
        if (verbose) {
            counter = new LoggingStyleSheetCounter(counter, getLog());
        }
        validators.add(new RulesLimitValidator(rulesLimit, counter));
        validators.add(new StylePropertiesLimitValidator(maxRules, getLog()));

        for (final StyleSheetValidator validator : validators) {
            validator.validate(stylesheet);
        }
    }

    private void saveParts(final File source, final List<StyleSheet> parts) throws MojoFailureException {
        final OrderedTree<StyleSheet> stylesheetsTree = new OrderedTree<StyleSheet>(parts, maxImports);

        // TODO valid deep

        if (verbose) {
            getLog().info("Saving CSS code...");
        }
        saveStyleSheetsTree(source, stylesheetsTree, 0, createCacheToken());
    }

    private String createCacheToken() {
        if (TokenType.NONE.name().equalsIgnoreCase(cacheTokenType)) {
            return "";
        } else {
            final String value = TokenType.create(cacheTokenType).createFactory().create(cacheTokenValue);
            final StringBuilder cacheToken = new StringBuilder();
            cacheToken.append('?');
            cacheToken.append(UrlEscaper.escape(cacheTokenParameter));
            cacheToken.append('=');
            cacheToken.append(UrlEscaper.escape(value));
            return cacheToken.toString();
        }
    }

    private int saveStyleSheetsTree(final File source, final TreeNode<StyleSheet> node, final int nodeIndex, final String cacheToken)
            throws MojoFailureException {
        final DestinationFileCreator fileCreator = new DestinationFileCreator(sourceDirectory, outputDirectory);
        if (nodeIndex == 0) {
            fileCreator.setFileNamePattern(outputFileNamePattern);
        } else {
            fileCreator.setFileNamePattern(outputPartNamePattern.replace(PART_INDEX_PARAMETER, String.valueOf(nodeIndex)));
        }
        final File target = fileCreator.create(source);

        if (node.hasValue()) {
            saveCss(target, node.getValue().toString());
            return nodeIndex;
        }

        final StringBuilder imports = new StringBuilder();
        int childNodeIndex = nodeIndex;
        for (final TreeNode<StyleSheet> child : node.getChildren()) {
            ++childNodeIndex;
            fileCreator.setFileNamePattern(outputPartNamePattern.replace(PART_INDEX_PARAMETER, String.valueOf(childNodeIndex)));
            final File childTarget = fileCreator.create(source);
            childNodeIndex = saveStyleSheetsTree(source, child, childNodeIndex, cacheToken);
            imports.append(String.format("@import \"%s%s\";%n", childTarget.getName(), cacheToken));
        }
        saveCss(target, imports.toString());
        return childNodeIndex;
    }

    private void saveCss(final File file, final String css) throws MojoFailureException {
        if (getLog().isDebugEnabled()) {
            getLog().debug("Saving CSS code to " + file.getAbsolutePath());
        }
        try {
            FileUtils.write(file, css, encoding);
        } catch (final IOException e) {
            throw new MojoFailureException("Cannot save file: " + file.getAbsolutePath(), e);
        }
    }
}
