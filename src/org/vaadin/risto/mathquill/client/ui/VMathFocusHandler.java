package org.vaadin.risto.mathquill.client.ui;

public class VMathFocusHandler {

    private static VMathField focusedMathField;

    public static VMathField getFocusedMathField() {
        return focusedMathField;
    }

    public static void setFocusedMathTextField(VMathField newFocusedField) {
        focusedMathField = newFocusedField;
    }

    public static boolean hasFocusedMathField() {
        return focusedMathField != null;
    }

    public static void removeFocus(VMathField field) {
        if (hasFocusedMathField() && getFocusedMathField() == field) {
            setFocusedMathTextField(null);
        }

    }
}
