package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;

public class MathElementInserter {

    public static final String CONTENTMARKER = "{}";

    /**
     * Insert a new latex element to the current cursor position. If we have a
     * selection, the selection either replaced or put inside the first content
     * marker.
     * 
     * Afterwards the math element is focused.
     * 
     * @param mathquillElement
     * @param latex
     */
    public static void insertNewElement(Element mathquillElement, String latex) {
        int stepBackCount = calculateStepBack(latex);

        if (MathJsBridge.hasSelection(mathquillElement) && stepBackCount > 0) {
            String selection = MathJsBridge.getSelection(mathquillElement);
            if (latex.endsWith("{}")) {
                selection = quoteReplacement(selection);
                latex = latex.replaceFirst("\\{\\}", "{" + selection + "}");

            }
            MathJsBridge.insertMath(mathquillElement, latex);
            MathJsBridge.stepBack(mathquillElement, stepBackCount - 1);

        } else {
            MathJsBridge.insertMath(mathquillElement, latex);
            MathJsBridge.stepBack(mathquillElement, stepBackCount);
        }

        MathJsBridge.focusElement(mathquillElement);
    }

    /**
     * Recursively calculate how many steps back we have to take so we can focus
     * the first content marker
     * 
     * @param latex
     * @return
     */
    protected static int calculateStepBack(String latex) {
        if (!latex.endsWith(CONTENTMARKER)) {
            return 0;
        } else {
            return 1 + calculateStepBack(latex.substring(0, latex.length()
                    - CONTENTMARKER.length()));
        }
    }

    protected static String quoteReplacement(String s) {
        if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
            return s;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                sb.append('\\');
                sb.append('\\');
            } else if (c == '$') {
                sb.append('\\');
                sb.append('$');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
