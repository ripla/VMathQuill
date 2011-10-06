package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VConsole;

public class VMathTextField extends HTML implements Paintable {

    public static final String CLASSNAME = "v-mathtextfield";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    private ApplicationConnection client;

    private Element innerElement;

    private boolean mixedMode = false;

    private HandlerRegistration blurHandler;

    public VMathTextField() {
        super();
        setStyleName(CLASSNAME);
        innerElement = DOM.createSpan();
        getElement().appendChild(innerElement);
        MathJsBridge.mathifyEditable(innerElement);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        BlurHandler handler = createBlurHandler();
        this.addHandler(handler, BlurEvent.getType());
    }

    @Override
    protected void onDetach() {
        removeBlurHandler();
        super.onDetach();
    }

    protected void removeBlurHandler() {
        if (blurHandler != null) {
            blurHandler.removeHandler();
        }
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            // no changes, no update
            return;
        }

        this.client = client;

        paintableId = uidl.getId();

        boolean serverMixedMode = uidl
                .getBooleanAttribute(Communication.ATT_MIXEDMODE);
        String serverContent = uidl
                .getStringAttribute(Communication.ATT_CONTENT);

        if (serverMixedMode != mixedMode) {
            mixedMode = serverMixedMode;

            resetInnerElement(serverContent);

            if (mixedMode) {
                MathJsBridge.mathifyTextBox(innerElement);
            } else {
                MathJsBridge.mathifyEditable(innerElement);
            }
        } else {
            MathJsBridge.setMathContent(innerElement, serverContent);
            MathJsBridge.updateMath(innerElement);
        }
        Util.notifyParentOfSizeChange(this, true);
    }

    private void resetInnerElement(String serverContent) {
        getElement().removeChild(innerElement);
        innerElement = DOM.createSpan();
        innerElement.setInnerHTML(serverContent);
        getElement().appendChild(innerElement);

    }

    private BlurHandler createBlurHandler() {
        return new BlurHandler() {
            public void onBlur(BlurEvent event) {
                EventTarget target = event.getNativeEvent().getEventTarget();
                boolean eventTargetsField = getElement().isOrHasChild(
                        Element.as(target));

                if (eventTargetsField) {
                    VConsole.log("BlurEvent targets field!");
                }
            }

        };
    }
}
