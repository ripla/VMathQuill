package org.vaadin.risto.mathquill;

import java.util.Map;

import org.vaadin.risto.mathquill.client.ui.Communication;

import com.vaadin.data.Property;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;

/**
 * <p>
 * Field that uses the MathQuill javascript library to render editable math
 * symbols in the browser.
 * </p>
 * 
 * <p>
 * This text field supports two modes: mixed and normal. When in normal
 * (default) mode, everything typed in this field is rendered as math. But when
 * this field is set into mixed mode, only content between <code>$</code> signs
 * is rendered. See {@link #setMixedMode(boolean)}
 * </p>
 * 
 * <p>
 * MathTextField also supports adding math elements to the current cursor
 * position. See {@link #addMathElement(String)}
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * @see MathLabel
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathTextField.class)
public class MathTextField extends AbstractField {

    private static final long serialVersionUID = 1446152150503621276L;
    private boolean mixedMode;
    private String elementToAdd;

    public MathTextField() {
        this(null);
    }

    public MathTextField(String caption) {
        this(caption, null);
    }

    public MathTextField(String caption, Property newDatasource) {
        setCaption(caption);
        setPropertyDataSource(newDatasource);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // paint either the pending math element, or the value, not both
        if (hasPendingMathElement()) {
            target.startTag("newMathElement");
            target.addAttribute(Communication.ATT_ELEMENTLATEX, elementToAdd);
            target.endTag(Communication.TAG_MATHELEMENT);

            clearPendingMathElement();
        } else if (hasValue()) {
            target.addVariable(this, Communication.ATT_CONTENT, getValue());
        }

        target.addAttribute(Communication.ATT_MIXEDMODE, isMixedMode());
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        super.changeVariables(source, variables);
        if (variables.containsKey(Communication.ATT_CONTENT)) {
            String newValue = (String) variables.get(Communication.ATT_CONTENT);
            setValue(newValue, true);
        }
    }

    public boolean hasValue() {
        return getValue() != null && !"".equals(getValue());
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public String getValue() {
        return (String) super.getValue();
    }

    @Override
    public void setValue(Object newValue) throws ReadOnlyException,
            ConversionException {
        super.setValue(newValue);
        requestRepaint();
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        if (newDataSource != null && newDataSource.getType() != String.class) {
            throw new IllegalArgumentException(
                    "Datasources of MathTextField must be of type String. Given: "
                            + newDataSource);
        }

        super.setPropertyDataSource(newDataSource);
    }

    /**
     * If set to true, content is rendered as math only when it is placed
     * between <code>$</code> signs.
     * 
     * @param mixedMode
     */
    public void setMixedMode(boolean mixedMode) {
        this.mixedMode = mixedMode;
        requestRepaint();
    }

    /**
     * @see #setMixedMode(boolean)
     */
    public boolean isMixedMode() {
        return mixedMode;
    }

    /**
     * Add a math element to the current (or last) cursor position. If the
     * element contains content markers, "{}", then the cursor will be moved to
     * the first one.
     * 
     * If the user has selected text, the text is either replaced or placed
     * inside the first content marker.
     * 
     * @param mathElement
     */
    public void addMathElement(String mathElement) {
        if (mathElement == null || mathElement.isEmpty()) {
            throw new IllegalArgumentException("Math element cannot be empty");
        }
        elementToAdd = mathElement;
        requestRepaint();
    }

    protected boolean hasPendingMathElement() {
        return elementToAdd != null;
    }

    protected void clearPendingMathElement() {
        elementToAdd = null;
    }
}
