package org.vaadin.risto.mathquill;

public class MathElement {

    private String latex;

    public MathElement(String latex) {
        if (latex == null || latex.isEmpty()) {
            throw new IllegalArgumentException(
                    "Latex content of a MathElement cannot be empty");
        }
        setLatex(latex);
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String content) {
        this.latex = content;
    }
}
