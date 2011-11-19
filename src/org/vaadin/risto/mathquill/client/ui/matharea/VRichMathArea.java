package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextArea;
import org.vaadin.risto.mathquill.client.ui.external.VRichTextToolbar;

import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.user.client.ui.RichTextArea;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;

public class VRichMathArea extends VRichTextArea {

    private static final String MATHAREA_CLASSNAME = "v-richmatharea";

    public VRichMathArea() {
        addStyleName(MATHAREA_CLASSNAME);
    }

    @Override
    protected VRichTextToolbar createToolbar(RichTextArea rta) {
        return new VRichMathAreaToolbar(rta);
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

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

        // make sure values from server have click handlers
        getToolbar().getHandler().reAttachClickHandlers();
    }
}
