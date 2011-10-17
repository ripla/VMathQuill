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
class EventHandler implements ClickHandler, ChangeHandler, KeyUpHandler {

    /**
     * 
     */
    private final VRichTextToolbar toolbarEventHandler;

    /**
     * @param vRichTextToolbar
     */
    EventHandler(VRichTextToolbar vRichTextToolbar) {
        toolbarEventHandler = vRichTextToolbar;
    }

    @SuppressWarnings("deprecation")
    public void onChange(ChangeEvent event) {
        Object sender = event.getSource();
        if (sender == toolbarEventHandler.backColors) {
            toolbarEventHandler.basic
                    .setBackColor(toolbarEventHandler.backColors
                            .getValue(toolbarEventHandler.backColors
                                    .getSelectedIndex()));
            toolbarEventHandler.backColors.setSelectedIndex(0);
        } else if (sender == toolbarEventHandler.foreColors) {
            toolbarEventHandler.basic
                    .setForeColor(toolbarEventHandler.foreColors
                            .getValue(toolbarEventHandler.foreColors
                                    .getSelectedIndex()));
            toolbarEventHandler.foreColors.setSelectedIndex(0);
        } else if (sender == toolbarEventHandler.fonts) {
            toolbarEventHandler.basic.setFontName(toolbarEventHandler.fonts
                    .getValue(toolbarEventHandler.fonts.getSelectedIndex()));
            toolbarEventHandler.fonts.setSelectedIndex(0);
        } else if (sender == toolbarEventHandler.fontSizes) {
            toolbarEventHandler.basic
                    .setFontSize(VRichTextToolbar.fontSizesConstants[toolbarEventHandler.fontSizes
                            .getSelectedIndex() - 1]);
            toolbarEventHandler.fontSizes.setSelectedIndex(0);
        }
    }

    @SuppressWarnings("deprecation")
    public void onClick(ClickEvent event) {
        Object sender = event.getSource();
        if (sender == toolbarEventHandler.bold) {
            toolbarEventHandler.basic.toggleBold();
        } else if (sender == toolbarEventHandler.italic) {
            toolbarEventHandler.basic.toggleItalic();
        } else if (sender == toolbarEventHandler.underline) {
            toolbarEventHandler.basic.toggleUnderline();
        } else if (sender == toolbarEventHandler.subscript) {
            toolbarEventHandler.basic.toggleSubscript();
        } else if (sender == toolbarEventHandler.superscript) {
            toolbarEventHandler.basic.toggleSuperscript();
        } else if (sender == toolbarEventHandler.strikethrough) {
            toolbarEventHandler.extended.toggleStrikethrough();
        } else if (sender == toolbarEventHandler.indent) {
            toolbarEventHandler.extended.rightIndent();
        } else if (sender == toolbarEventHandler.outdent) {
            toolbarEventHandler.extended.leftIndent();
        } else if (sender == toolbarEventHandler.justifyLeft) {
            toolbarEventHandler.basic
                    .setJustification(RichTextArea.Justification.LEFT);
        } else if (sender == toolbarEventHandler.justifyCenter) {
            toolbarEventHandler.basic
                    .setJustification(RichTextArea.Justification.CENTER);
        } else if (sender == toolbarEventHandler.justifyRight) {
            toolbarEventHandler.basic
                    .setJustification(RichTextArea.Justification.RIGHT);
        } else if (sender == toolbarEventHandler.insertImage) {
            final String url = Window.prompt("Enter an image URL:", "http://");
            if (url != null) {
                toolbarEventHandler.extended.insertImage(url);
            }
        } else if (sender == toolbarEventHandler.createLink) {
            final String url = Window.prompt("Enter a link URL:", "http://");
            if (url != null) {
                toolbarEventHandler.extended.createLink(url);
            }
        } else if (sender == toolbarEventHandler.removeLink) {
            toolbarEventHandler.extended.removeLink();
        } else if (sender == toolbarEventHandler.hr) {
            toolbarEventHandler.extended.insertHorizontalRule();
        } else if (sender == toolbarEventHandler.ol) {
            toolbarEventHandler.extended.insertOrderedList();
        } else if (sender == toolbarEventHandler.ul) {
            toolbarEventHandler.extended.insertUnorderedList();
        } else if (sender == toolbarEventHandler.removeFormat) {
            toolbarEventHandler.extended.removeFormat();
        } else if (sender == toolbarEventHandler.richText) {
            // We use the RichTextArea's onKeyUp event to update the toolbar
            // status. This will catch any cases where the user moves the
            // cursur using the keyboard, or uses one of the browser's
            // built-in keyboard shortcuts.
            toolbarEventHandler.updateStatus();
        }
    }

    public void onKeyUp(KeyUpEvent event) {
        if (event.getSource() == toolbarEventHandler.richText) {
            // We use the RichTextArea's onKeyUp event to update the toolbar
            // status. This will catch any cases where the user moves the
            // cursor using the keyboard, or uses one of the browser's
            // built-in keyboard shortcuts.
            toolbarEventHandler.updateStatus();
        }
    }
}