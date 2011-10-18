package org.vaadin.risto.mathquill.client.ui.external;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * We use an inner EventHandler class to avoid exposing event methods on the
 * RichTextToolbar itself.
 */
// copied due private variables
public class VRichTextAreaEventHandler implements ClickHandler, ChangeHandler,
        KeyUpHandler {

    private VRichTextToolbar richTextToolbar;

    /**
     * @param vRichTextToolbar
     */
    public VRichTextAreaEventHandler(VRichTextToolbar vRichTextToolbar) {
        setRichTextToolbar(vRichTextToolbar);
    }

    @SuppressWarnings("deprecation")
    public void onChange(ChangeEvent event) {
        Object sender = event.getSource();
        if (sender == getRichTextToolbar().backColors) {
            getRichTextToolbar().getBasic()
                    .setBackColor(getRichTextToolbar().backColors
                            .getValue(getRichTextToolbar().backColors
                                    .getSelectedIndex()));
            getRichTextToolbar().backColors.setSelectedIndex(0);
        } else if (sender == getRichTextToolbar().foreColors) {
            getRichTextToolbar().getBasic()
                    .setForeColor(getRichTextToolbar().foreColors
                            .getValue(getRichTextToolbar().foreColors
                                    .getSelectedIndex()));
            getRichTextToolbar().foreColors.setSelectedIndex(0);
        } else if (sender == getRichTextToolbar().fonts) {
            getRichTextToolbar().getBasic().setFontName(getRichTextToolbar().fonts
                    .getValue(getRichTextToolbar().fonts.getSelectedIndex()));
            getRichTextToolbar().fonts.setSelectedIndex(0);
        } else if (sender == getRichTextToolbar().fontSizes) {
            getRichTextToolbar().getBasic()
                    .setFontSize(VRichTextToolbar.fontSizesConstants[getRichTextToolbar().fontSizes
                            .getSelectedIndex() - 1]);
            getRichTextToolbar().fontSizes.setSelectedIndex(0);
        }
    }

    @SuppressWarnings("deprecation")
    public void onClick(ClickEvent event) {
        Object sender = event.getSource();
        if (sender == getRichTextToolbar().bold) {
            getRichTextToolbar().getBasic().toggleBold();
        } else if (sender == getRichTextToolbar().italic) {
            getRichTextToolbar().getBasic().toggleItalic();
        } else if (sender == getRichTextToolbar().underline) {
            getRichTextToolbar().getBasic().toggleUnderline();
        } else if (sender == getRichTextToolbar().subscript) {
            getRichTextToolbar().getBasic().toggleSubscript();
        } else if (sender == getRichTextToolbar().superscript) {
            getRichTextToolbar().getBasic().toggleSuperscript();
        } else if (sender == getRichTextToolbar().strikethrough) {
            getRichTextToolbar().getExtended().toggleStrikethrough();
        } else if (sender == getRichTextToolbar().indent) {
            getRichTextToolbar().getExtended().rightIndent();
        } else if (sender == getRichTextToolbar().outdent) {
            getRichTextToolbar().getExtended().leftIndent();
        } else if (sender == getRichTextToolbar().justifyLeft) {
            getRichTextToolbar().getBasic()
                    .setJustification(RichTextArea.Justification.LEFT);
        } else if (sender == getRichTextToolbar().justifyCenter) {
            getRichTextToolbar().getBasic()
                    .setJustification(RichTextArea.Justification.CENTER);
        } else if (sender == getRichTextToolbar().justifyRight) {
            getRichTextToolbar().getBasic()
                    .setJustification(RichTextArea.Justification.RIGHT);
        } else if (sender == getRichTextToolbar().insertImage) {
            final String url = Window.prompt("Enter an image URL:", "http://");
            if (url != null) {
                getRichTextToolbar().getExtended().insertImage(url);
            }
        } else if (sender == getRichTextToolbar().createLink) {
            final String url = Window.prompt("Enter a link URL:", "http://");
            if (url != null) {
                getRichTextToolbar().getExtended().createLink(url);
            }
        } else if (sender == getRichTextToolbar().removeLink) {
            getRichTextToolbar().getExtended().removeLink();
        } else if (sender == getRichTextToolbar().hr) {
            getRichTextToolbar().getExtended().insertHorizontalRule();
        } else if (sender == getRichTextToolbar().ol) {
            getRichTextToolbar().getExtended().insertOrderedList();
        } else if (sender == getRichTextToolbar().ul) {
            getRichTextToolbar().getExtended().insertUnorderedList();
        } else if (sender == getRichTextToolbar().removeFormat) {
            getRichTextToolbar().getExtended().removeFormat();
        } else if (sender == getRichTextToolbar().getRichText()) {
            // We use the RichTextArea's onKeyUp event to update the toolbar
            // status. This will catch any cases where the user moves the
            // cursur using the keyboard, or uses one of the browser's
            // built-in keyboard shortcuts.
            getRichTextToolbar().updateStatus();
        }
    }

    public void onKeyUp(KeyUpEvent event) {
        if (event.getSource() == getRichTextToolbar().getRichText()) {
            // We use the RichTextArea's onKeyUp event to update the toolbar
            // status. This will catch any cases where the user moves the
            // cursor using the keyboard, or uses one of the browser's
            // built-in keyboard shortcuts.
            getRichTextToolbar().updateStatus();
        }
    }

    public VRichTextToolbar getRichTextToolbar() {
        return richTextToolbar;
    }

    public void setRichTextToolbar(VRichTextToolbar richTextToolbar) {
        this.richTextToolbar = richTextToolbar;
    }
}