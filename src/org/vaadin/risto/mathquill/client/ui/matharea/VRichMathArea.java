package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextArea;
import org.vaadin.risto.mathquill.client.ui.external.VRichTextToolbar;

import com.google.gwt.user.client.ui.RichTextArea;

public class VRichMathArea extends VRichTextArea {

    @Override
    protected VRichTextToolbar createToolbar(RichTextArea rta) {
        return new VRichMathAreaToolbar(rta);
    }

    protected String getSelection() {
        return RichTextJs.getSelection(getElement()).get(0);
    }
}
