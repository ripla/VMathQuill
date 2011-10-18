package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.UIObject;

public class MathEventHandler extends VRichTextAreaEventHandler {

    private final RichTextArea textArea;
    private final MathPopup mathPopup;

    public MathEventHandler(VRichMathAreaToolbar toolbar, RichTextArea mathArea) {
        super(toolbar);
        this.textArea = mathArea;
        mathPopup = new MathPopup();

        mathPopup.setCallback(new MathPopup.Callback() {
            public void aswerIsYes(boolean yes) {
                if (yes) {
                    String latex = mathPopup.getLatexValue();
                    getTextArea().getFormatter().insertHTML(latex);

                    // experimental
                    // Element span = DOM.createSpan();
                    // MathJsBridge.makeMathElement(span);
                    // MathJsBridge.setMathContent(span, latex);
                    // RichTextJs.append(textArea.getElement(), span);
                }
            }
        });
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == getRichTextToolbar().getMathifyButton()) {
            String selection = RichTextJs.getSelection(
                    getTextArea().getElement()).get(0);

            mathPopup.setContents(selection);

            mathPopup.showRelativeTo((UIObject) event.getSource());

            if (selection != null && !selection.isEmpty()) {
                mathPopup.setButtonText("Apply");

            } else {
                mathPopup.setButtonText("Add");
            }
        } else {
            super.onClick(event);
        }
    }

    @Override
    public VRichMathAreaToolbar getRichTextToolbar() {
        return (VRichMathAreaToolbar) super.getRichTextToolbar();
    }

    public RichTextArea getTextArea() {
        return textArea;
    }

}
