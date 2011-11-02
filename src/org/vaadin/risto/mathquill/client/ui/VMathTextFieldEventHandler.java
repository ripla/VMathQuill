package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;

/**
 * Handles events related to focus on the MathQuill element.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class VMathTextFieldEventHandler {

    public boolean shouldLoseFocusFor(NativePreviewEvent event) {
        return !isMouseMoveEvent(event);
    }

    public boolean validEventTargetsThis(Element hostElement,
            NativePreviewEvent event) {
        return targetsThis(hostElement, event) && !isMouseMoveEvent(event);
    }

    public boolean isKeyboardEvent(NativePreviewEvent event) {
        return (event.getTypeInt() & Event.KEYEVENTS) != 0;
    }

    private boolean targetsThis(Element hostElement, NativePreviewEvent event) {
        NativeEvent nativeEvent = event.getNativeEvent();
        EventTarget target = nativeEvent.getEventTarget();

        return hostElement.isOrHasChild(Element.as(target));
    }

    private boolean isMouseMoveEvent(NativePreviewEvent event) {
        return (event.getTypeInt() & (Event.ONMOUSEMOVE | Event.ONMOUSEOUT | Event.ONMOUSEOVER)) != 0;
    }

    public boolean isValidCursorRefreshEvent(NativePreviewEvent event) {
        return (event.getTypeInt() & (Event.ONCLICK | Event.KEYEVENTS)) != 0;
    }
}
