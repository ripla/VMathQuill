package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VMathTextField extends TextBoxBase implements Paintable {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-mathtextfield";

    private static DivElement baseElement;

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    public VMathTextField() {
        super(baseElement = Document.get().createDivElement());

        setElement(baseElement);

        setStyleName(CLASSNAME);
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            // no changes, no update
            return;
        }

        this.client = client;

        paintableId = uidl.getId();

        // TODO
        getElement().setInnerHTML("It works!");
    }

}
