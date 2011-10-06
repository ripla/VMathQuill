package org.vaadin.risto.mathquill;

import org.vaadin.risto.mathquill.client.ui.Communication;

import com.vaadin.data.Property;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;

/**
 * <p>
 * TextField that uses the MathQuill javascript library to render editable math
 * symbols in the browser.
 * </p>
 * 
 * <p>
 * This textfield supports two modes: mixed and normal. When in normal (default)
 * mode, everything typed in this field is rendered as math. But when this field
 * is set into mixed mode, only content between <code>$</code> signs is
 * rendered.
 * </p>
 * 
 * @see MathLabel
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathTextField.class)
public class MathTextField extends AbstractField {

    private static final long serialVersionUID = 1446152150503621276L;
    private boolean mixedMode;

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

        if (hasValue()) {
            target.addAttribute(Communication.ATT_CONTENT, getValue());
        }

        target.addAttribute(Communication.ATT_MIXEDMODE, isMixedMode());
    }

    private boolean hasValue() {
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
        if (newDataSource.getType() != String.class) {
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
    }

    /**
     * @see #setMixedMode(boolean)
     */
    public boolean isMixedMode() {
        return mixedMode;
    }
}
