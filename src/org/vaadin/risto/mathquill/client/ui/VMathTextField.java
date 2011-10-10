package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
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
    private ApplicationConnection client;

    private Element innerElement;

    private boolean mixedMode = false;

    private HandlerRegistration handlerRegistration;

    private boolean hasFocus;

    private final boolean immediate = true;

    private final EventHandler eventHandler;

    public VMathTextField() {
        super();
        setStyleName(CLASSNAME);

        eventHandler = new EventHandler();

        innerElement = DOM.createSpan();
        getElement().appendChild(innerElement);
        MathJsBridge.mathifyEditable(innerElement);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        handlerRegistration = Event
                .addNativePreviewHandler(new NativePreviewHandler() {
                    public void onPreviewNativeEvent(NativePreviewEvent event) {
                        handlePreviewEvent(event);
                    }

                });
    }

    protected void handlePreviewEvent(NativePreviewEvent event) {
        if (eventHandler.validEventTargetsThis(getElement(), event)) {
            if (!hasFocus) {
                hasFocus = true;
            }
        } else if (hasFocus
                && eventHandler.shouldLoseFocusFor(getElement(), event)) {
            hasFocus = false;
            fireFocusLost();
        }
    }

    protected void fireFocusLost() {
        String value = MathJsBridge.getMathValue(innerElement);
        client.updateVariable(paintableId, Communication.ATT_CONTENT, value,
                immediate);
    }

    @Override
    protected void onDetach() {
        removeHandler();
        super.onDetach();
    }

    protected void removeHandler() {
        if (handlerRegistration != null) {
            handlerRegistration.removeHandler();
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
                .getStringVariable(Communication.ATT_CONTENT);

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
        }

        Util.notifyParentOfSizeChange(this, true);

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                MathJsBridge.updateMath(innerElement);
            }
        });
    }

    private void resetInnerElement(String serverContent) {
        getElement().removeChild(innerElement);
        innerElement = DOM.createSpan();
        innerElement.setInnerHTML(serverContent);
        getElement().appendChild(innerElement);

    }

}
