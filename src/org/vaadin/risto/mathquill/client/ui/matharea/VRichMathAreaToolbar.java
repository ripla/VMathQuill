package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;
import org.vaadin.risto.mathquill.client.ui.external.VRichTextToolbar;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;

public class VRichMathAreaToolbar extends VRichTextToolbar {

    protected PushButton mathifyButton;

    public VRichMathAreaToolbar(RichTextArea richText) {
        super(richText);
    }

    @Override
    protected void createExtendedTopPanel(FlowPanel topPanel) {
        super.createExtendedTopPanel(topPanel);

        topPanel.add(mathifyButton = createPushButton(images.mathify(),
                strings.mathify()));
    }

    @Override
    protected VRichTextAreaEventHandler createEventHandler() {
        return new MathEventHandler(this, getRichText());
    }

    public PushButton getMathifyButton() {
        return mathifyButton;
    }

    @Override
    public MathEventHandler getHandler() {
        return (MathEventHandler) super.getHandler();
    }
}
