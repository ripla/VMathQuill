package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VLabel;

public class VMathLabel extends VLabel implements Paintable {

    protected String paintableId;

    protected ApplicationConnection client;

    public VMathLabel() {
        super();
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            // no changes, no update
            return;
        }

        this.client = client;

        paintableId = uidl.getId();

        setText(uidl.getChildString(0));

        mathify(this.getElement());
    }

    public static native void mathify(Element e) /*-{ 
                                                 $wnd.$(e).mathquill()
                                                 
                                                 }-*/;

}
