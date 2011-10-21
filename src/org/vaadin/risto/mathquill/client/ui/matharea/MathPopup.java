package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.MathJsBridge;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class MathPopup extends PopupPanel {

    private static final String PRIMARYSTYLENAME = "richtext-mathpopup";

    public interface Callback {
        public void aswerIsYes(boolean yes);
    }

    private Element mathTextBox;
    private Callback callback;
    private Button yes;

    public MathPopup() {
        super(true);
        addStyleName(PRIMARYSTYLENAME);
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
}