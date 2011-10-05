package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;

public class VMathLabel extends HTML implements Paintable {
    public static final String CLASSNAME = "v-mathlabel";

    protected String paintableId;

    protected ApplicationConnection client;

    private final com.google.gwt.user.client.Element innerElement;

    public VMathLabel() {
        super();
        setStyleName(CLASSNAME);
        innerElement = DOM.createSpan();
        getElement().appendChild(innerElement);
        MathJsBridge.makeMathElement(innerElement);
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
