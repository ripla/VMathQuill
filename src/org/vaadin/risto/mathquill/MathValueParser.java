package org.vaadin.risto.mathquill;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Helper methods for parsing the values from a {@link RichMathArea}. Singleton
 * object.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class MathValueParser {

    public static MathValueParser get() {
        return new MathValueParser();
    }

    protected MathValueParser() {

    }

    /**
     * Returns the value of the given {@link RichMathArea} with all the math
     * values parsed to latex.
     * 
     * @param mathArea
     * @return
     */
    public String getMathValue(RichMathArea mathArea) {
        if (mathArea == null) {
            throw new IllegalArgumentException("MathArea cannot be null");
        }

        String value = (String) mathArea.getValue();
        if (value != null) {
            return replaceMathPlaceHoldersWithLatex(value);
        } else {
            return "";
        }
    }

    /**
     * Returns the value of the given {@link RichMathArea} as a list of Vaadin
     * components, with separate components for text and math.
     * 
     * @param mathArea
     * @return
     */
    public List<Component> getMathValueAsComponents(RichMathArea mathArea) {
        if (mathArea == null) {
            throw new IllegalArgumentException("MathArea cannot be null");
        }
        String value = (String) mathArea.getValue();
        if (value != null) {
            return parseValueToComponents(value);
        } else {
            return Collections.emptyList();
        }
    }

    // TODO this is overly complicated -> rewrite
    protected List<Component> parseValueToComponents(String value) {
        int mathPlaceHolders = calculateMathInstances(value);

        Pattern replacementImagePattern = Pattern
                .compile("<img.*?class=\"latexPlaceHolder\".*?>");

        int textStart = 0;
        List<Component> componentList = new LinkedList<Component>();

        Matcher replacementImageMatcher = replacementImagePattern
                .matcher(value);

        for (int i = 0; i < mathPlaceHolders; i++) {
            replacementImageMatcher.find();
            MatchResult result = replacementImageMatcher.toMatchResult();
            String imgTag = result.group();
            String latexValue = parseLatexValueFromImageTag(imgTag);

            Component math = createMathComponent(latexValue);

            if (textStart != result.start()) {
                String textValue = value.substring(textStart, result.start());
                Component text = createTextComponent(textValue);
                componentList.add(text);
            }

            componentList.add(math);
            textStart = result.end();
        }

        if (textStart != value.length()) {
            String textValue = value.substring(textStart, value.length());
            Component text = createTextComponent(textValue);
            componentList.add(text);
        }

        return componentList;
    }

    protected Component createTextComponent(String textValue) {
        return new Label(textValue, Label.CONTENT_XHTML);
    }

    protected Component createMathComponent(String latexValue) {
        return new MathLabel(latexValue);
    }

    protected String replaceMathPlaceHoldersWithLatex(String value) {
        int mathPlaceHolders = calculateMathInstances(value);
        StringBuffer mathifiedValue = new StringBuffer(value);

        Pattern replacementImagePattern = Pattern
                .compile("<img.*?class=\"latexPlaceHolder\".*?>");

        for (int i = 0; i < mathPlaceHolders; i++) {
            Matcher replacementImageMatcher = replacementImagePattern
                    .matcher(mathifiedValue);
            replacementImageMatcher.find();
            MatchResult result = replacementImageMatcher.toMatchResult();
            String imgTag = result.group();

            String latexValue = parseLatexValueFromImageTag(imgTag);
            mathifiedValue.replace(result.start(), result.end(), latexValue);
        }

        return mathifiedValue.toString();
    }

    protected String parseLatexValueFromImageTag(String imgTag) {
        Pattern latexValuePattern = Pattern.compile("title=\"(.*?)\"");
        Matcher latexValueMatcher = latexValuePattern.matcher(imgTag);
        latexValueMatcher.find();
        return latexValueMatcher.group(1);
    }

    protected int calculateMathInstances(String value) {
        Pattern pattern = Pattern.compile("class=\"(latexPlaceHolder)\"");
        int matches = 0;
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
