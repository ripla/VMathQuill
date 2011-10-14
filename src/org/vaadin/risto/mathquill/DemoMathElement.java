package org.vaadin.risto.mathquill;

/**
 * Created for the sole purpose of easier demoing the use of MathElement in the
 * UI.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public enum DemoMathElement {

    ALPHA("alpha", new MathElement("\\alpha")), BETA("beta", new MathElement(
            "\\beta")), FRACTION("fraction", new MathElement("\\frac{}{}")), SQRT(
            "square root", new MathElement("\\sqrt{}"));

    public final String html;
    public final MathElement element;

    private DemoMathElement(String html, MathElement element) {
        this.html = html;
        this.element = element;

    }
}
