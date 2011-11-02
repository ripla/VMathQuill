package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VGlobalMathToolbar extends SimplePanel implements Paintable {

    private final Panel contentPanel;
    private HandlerRegistration clickHandlerRegistration;

    public VGlobalMathToolbar() {
        contentPanel = new FlowPanel();
        setWidget(contentPanel);
        getElement().setId("globalMathToolbar");
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            // no changes, no update
            return;
        }

        contentPanel.clear();

        if (uidl.getChildCount() > 0) {
            for (int i = 0; i < uidl.getChildCount(); i++) {
                contentPanel.add(createToolbarButton(uidl.getChildUIDL(i)));
            }
        }
    }

    private Widget createToolbarButton(final UIDL buttonUIDL) {
        Button toolbarButton = new Button();
        toolbarButton.setHTML(buttonUIDL
                .getStringAttribute(Communication.ATT_TOOLBARBUTTONCAPTION));
        clickHandlerRegistration = toolbarButton
                .addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        handleClick(buttonUIDL
                                .getStringAttribute(Communication.ATT_TOOLBARCOMMAND));
                    }
                });

        return toolbarButton;
    }

    /**
     * Insert the given latex command to the currently focused math field
     * 
     * @param latexCommand
     */
    protected void handleClick(String latexCommand) {
        if (VMathFocusHandler.hasFocusedMathField()) {
            VMathFocusHandler.getFocusedMathField().insertNewElement(
                    latexCommand);
        }
    }

    @Override
    protected void onDetach() {
        clickHandlerRegistration.removeHandler();
        super.onDetach();
    }

}
