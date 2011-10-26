package org.vaadin.risto.mathquill;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vaadin.risto.mathquill.client.ui.matharea.VRichMathArea;

import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.RichTextArea;

@ClientWidget(VRichMathArea.class)
public class RichMathArea extends RichTextArea {

    private static final long serialVersionUID = 6213232525602680033L;

    public String getMathValue() {
        if (getValue() != null) {
            String value = (String) getValue();

            return replaceMathPlaceHoldersWithLatex(value);
        } else {
            return "";
        }
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
