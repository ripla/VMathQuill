package org.vaadin.risto.mathquill;

import org.vaadin.risto.mathquill.client.ui.Communication;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * Label that uses the MathQuill javascript library to render math symbols in
 * the browser.
 * 
 * @see MathTextField
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathLabel.class)
public class MathLabel extends AbstractComponent {

    private static final long serialVersionUID = -4700221613987326009L;
    private final String content;

    public MathLabel(String content) {
        this.content = content;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (content != null && !"".equals(content)) {
            target.addAttribute(Communication.ATT_CONTENT, content);
        }
    }

}
