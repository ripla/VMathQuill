package org.vaadin.risto.mathquill;

public class MathElement {

    private String latex;
    private int stepBack;
    private boolean hasContent;

    public MathElement(String latex) {
        setLatex(latex);
    }

    public MathElement(String latex, int stepBack) {
        this(latex);
        setStepBack(stepBack);
    }

    public MathElement(String latex, int stepBack, boolean hasContent) {
        this(latex, stepBack);
        setHasContent(hasContent);
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String content) {
        this.latex = content;
    }

    public int getStepBack() {
        return stepBack;
    }

    public void setStepBack(int stepBack) {
        this.stepBack = stepBack;
    }

    public boolean hasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }
}
