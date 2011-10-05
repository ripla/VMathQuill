package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;

public class VMathTextField extends HTML implements Paintable {

    public static final String CLASSNAME = "v-mathtextfield";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    private final Element innerElement;

    public VMathTextField() {
        super();
        setStyleName(CLASSNAME);
        innerElement = DOM.createSpan();
        getElement().appendChild(innerElement);
        MathJsBridge.mathifyEditable(innerElement);
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            // no changes, no update
            return;
        }

        this.client = client;

        paintableId = uidl.getId();

        if (uidl.hasAttribute(Communication.ATT_CONTENT)) {
            MathJsBridge.setMathContent(innerElement,
                    uidl.getStringAttribute(Communication.ATT_CONTENT));
        }

        MathJsBridge.updateMath(innerElement);
        Util.notifyParentOfSizeChange(this, true);
    }

}
