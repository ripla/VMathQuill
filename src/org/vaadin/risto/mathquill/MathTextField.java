package org.vaadin.risto.mathquill;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * TextField that uses the MathQuill javascript library to render editable math
 * symbols in the browser.
 * 
 * @see MathLabel
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathTextField.class)
public class MathTextField extends AbstractComponent {

    private static final long serialVersionUID = 1446152150503621276L;

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // TODO Paint any component specific content by setting attributes
        // These attributes can be read in updateFromUIDL in the widget.
    }

}
