package org.vaadin.risto.mathquill;

/**
 * Created for the sole purpose of easier demoing the use of MathElement in the
 * UI.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public enum DemoMathElement {

    ALPHA("alpha", "\\alpha"), BETA("beta", "\\beta"), FRACTION("fraction",
            "\\frac{}{}"), SQRT("square root", "\\sqrt{}");

    public final String html;
    public final String latex;

    private DemoMathElement(String html, String element) {
        this.html = html;
        this.latex = element;

    }
}
