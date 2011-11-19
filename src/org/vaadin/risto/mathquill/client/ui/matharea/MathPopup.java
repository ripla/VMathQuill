package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.MathElementInserter;
import org.vaadin.risto.mathquill.client.ui.MathJsBridge;
import org.vaadin.risto.mathquill.client.ui.VMathField;
import org.vaadin.risto.mathquill.client.ui.VMathFocusHandler;
import org.vaadin.risto.mathquill.client.ui.VMathTextFieldEventHandler;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class MathPopup extends VOverlay implements VMathField {

    private static final String PRIMARYSTYLENAME = "richtext-mathpopup";

    public interface Callback {
        public void aswerIsYes(boolean yes);
    }

    private Element mathTextBox;
    private Callback callback;
    private Button yes;
    private final VMathTextFieldEventHandler eventHandler;
    private boolean hasFocus;
    private HandlerRegistration handlerRegistration;
    private Element toolbarElement;

    public MathPopup() {
        super(true);
        addStyleName(PRIMARYSTYLENAME);
        eventHandler = new VMathTextFieldEventHandler();
    }

    @Override
    public void show() {
        // so that this is focused when first shown
        VMathFocusHandler.setFocusedMathTextField(this);

        // so that clicks on the toolbar buttons don't hide this
        toolbarElement = Document.get().getElementById("globalMathToolbar");
        if (toolbarElement != null) {
            addAutoHidePartner(toolbarElement);
        }

        super.show();
    }

    @Override
    public void hide() {
        if (toolbarElement != null) {
            removeAutoHidePartner(toolbarElement);
        }
        super.hide();
    }

    public void setContents(String contents) {
        clear();

        HorizontalPanel panel = new HorizontalPanel();
        panel.addStyleName(PRIMARYSTYLENAME + "-panel");

        mathTextBox = DOM.createSpan();
        Label mathBox = new Label();
        mathBox.getElement().appendChild(mathTextBox);
        MathJsBridge.mathifyEditable(mathTextBox);
        MathJsBridge.setMathContent(mathTextBox, contents);
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                MathJsBridge.updateMath(mathTextBox);
                MathJsBridge.focusElement(mathTextBox);
            }
        });

        yes = new Button();
        yes.addStyleName(PRIMARYSTYLENAME + "-yes");

        yes.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getCallBack().aswerIsYes(true);
                MathPopup.this.hide();
            }
        });

        panel.add(mathBox);
        panel.add(yes);
        panel.setCellVerticalAlignment(yes, HorizontalPanel.ALIGN_MIDDLE);
        panel.setCellHorizontalAlignment(yes, HorizontalPanel.ALIGN_RIGHT);

        this.add(panel);

        KeyDownHandler enterHandler = new KeyDownHandler() {

            public void onKeyDown(KeyDownEvent event) {
                if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
                    yes.click();
                }

            }
        };
        panel.addDomHandler(enterHandler, KeyDownEvent.getType());
    }

    public String getLatexValue() {
        return MathJsBridge.getMathValue(mathTextBox);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallBack() {
        return callback;
    }

    public void setButtonText(String caption) {
        yes.setText(caption);

    }

    public void insertNewElement(String latexCommand) {
        MathElementInserter.insertNewElement(mathTextBox, latexCommand);
    }

    protected void handlePreviewEvent(NativePreviewEvent event) {
        if (eventHandler.validEventTargetsThis(getElement(), event)) {
            if (!hasFocus) {
                hasFocus = true;

                // this is the currently focused field globally
                VMathFocusHandler.setFocusedMathTextField(this);
            }
        } else if (hasFocus && eventHandler.shouldLoseFocusFor(event)) {
            hasFocus = false;
        }
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

    @Override
    protected void onDetach() {
        handlerRegistration.removeHandler();

        // this cannot be the one focused field after it's detached
        VMathFocusHandler.removeFocus(this);

        super.onDetach();
    }
}