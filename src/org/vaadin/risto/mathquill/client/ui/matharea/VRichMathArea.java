package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextArea;
import org.vaadin.risto.mathquill.client.ui.external.VRichTextToolbar;

import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.user.client.ui.RichTextArea;

public class VRichMathArea extends VRichTextArea {

    private static final String CLASSNAME = "v-richmatharea";

    public VRichMathArea() {
        addStyleName(CLASSNAME);
    }

    @Override
    protected VRichTextToolbar createToolbar(RichTextArea rta) {
        return new VRichMathAreaToolbar(rta);
    }

    protected String getSelection() {
        return RichTextJs.getSelection(getElement()).get(0);
    }

    @Override
    protected RichTextArea createRichTextArea() {
        final RichTextArea rta = new RichTextArea();

        rta.addInitializeHandler(new InitializeHandler() {
            public void onInitialize(InitializeEvent event) {
                // workaround for the image resize bars in e.g. Mozilla
                RichTextJs.disableObjectResizing(rta.getElement());

                // re-attach latex image click handlers
                getToolbar().getHandler().reAttachClickHandlers();
            }
        });
        return rta;
    }

    @Override
    public VRichMathAreaToolbar getToolbar() {
        return (VRichMathAreaToolbar) super.getToolbar();
    }
}
