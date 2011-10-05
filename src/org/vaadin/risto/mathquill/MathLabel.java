package org.vaadin.risto.mathquill;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Label;

/**
 * Label that uses the MathQuill javascript library to render math symbols in
 * the browser.
 * 
 * @see MathTextField
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathLabel.class)
public class MathLabel extends Label {

    private static final long serialVersionUID = -4700221613987326009L;

    public MathLabel(String content) {
        super(content);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // TODO Paint any component specific content by setting attributes
        // These attributes can be read in updateFromUIDL in the widget.
    }

}
