package org.vaadin.risto.mathquill;

import org.vaadin.risto.mathquill.client.ui.matharea.VRichMathArea;

import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.RichTextArea;

/**
 * A modified version of the default Vaadin {@link com.vaadin.ui.RichTextArea
 * RichTextArea}. Adds a toolbar button for adding math values, and enables
 * editing them from a popup.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 */
@ClientWidget(VRichMathArea.class)
public class RichMathArea extends RichTextArea {

    private static final long serialVersionUID = 6213232525602680033L;

}
