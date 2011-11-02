package org.vaadin.risto.mathquill;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.risto.mathquill.client.ui.Communication;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;

/**
 * Allows the use of a global toolbar for inserting pre-defined math elements
 * into instances of {@link MathTextField}
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
@ClientWidget(org.vaadin.risto.mathquill.client.ui.VGlobalMathToolbar.class)
public class GlobalMathToolbar extends AbstractComponent {

    private static final long serialVersionUID = 720210505373657104L;
    private final Map<String, String> buttons = new LinkedHashMap<String, String>();

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);
        for (Entry<String, String> button : buttons.entrySet()) {
            target.startTag(Communication.TAG_TOOLBARBUTTONS);
            target.addAttribute(Communication.ATT_TOOLBARBUTTONCAPTION,
                    button.getKey());
            target.addAttribute(Communication.ATT_TOOLBARCOMMAND,
                    button.getValue());
            target.endTag(Communication.TAG_TOOLBARBUTTONS);
        }
    }

    public void addButton(String caption, String command) {
        if (caption == null || command == null) {
            throw new IllegalArgumentException(
                    "Caption and command cannot be null");
        }

        buttons.put(caption, command);

        requestRepaint();
    }

    public void removeButton(String caption) {
        if (caption == null) {
            throw new IllegalArgumentException("Caption cannot be null");
        }

        buttons.remove(caption);
        requestRepaint();
    }

    public void clearButtons() {
        buttons.clear();
        requestRepaint();
    }

    public String getButtonCommand(String caption) {
        if (caption == null) {
            throw new IllegalArgumentException("Caption cannot be null");
        }
        return buttons.get(caption);
    }

    public void setButtonCommand(String caption, String command) {
        if (caption == null || command == null) {
            throw new IllegalArgumentException(
                    "Caption and command cannot be null");
        }

        if (buttons.containsKey(caption)) {
            buttons.put(caption, command);
            requestRepaint();
        }
    }
}
