package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.UIObject;

public class MathEventHandler extends VRichTextAreaEventHandler {

    private final RichTextArea textArea;
    private final MathPopup mathPopup;

    public MathEventHandler(VRichMathAreaToolbar toolbar,
            final RichTextArea mathArea) {
        super(toolbar);
        this.textArea = mathArea;
        mathPopup = new MathPopup();
    }

    protected Element createLatexPlaceholder(final String latexContent) {
        // create image URL
        String src = URL.encode("http://latex.codecogs.com/gif.latex?"
                + latexContent);

        // create element
        final Element latexPlaceHolder = DOM.createImg();
        com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) latexPlaceHolder;

        // set element click handler
        DOM.sinkEvents(castElement, Event.ONCLICK);
        DOM.setEventListener(castElement, new EventListener() {

            public void onBrowserEvent(Event event) {
                handlePlaceHolderClick(Element.as(event.getEventTarget()),
                        latexContent);
            }
        });

        // set element content
        DOM.setElementProperty(castElement, "title", latexContent);
        DOM.setImgSrc(castElement, src);
        latexPlaceHolder.setPropertyString("className", "latexPlaceHolder");
        latexPlaceHolder.getStyle().setCursor(Cursor.POINTER);

        // set element unique id
        latexPlaceHolder.setId("latexPlaceHolder_" + latexContent + "_"
                + Math.random());
        return latexPlaceHolder;
    }

    protected void handlePlaceHolderClick(Element placeHolderElement,
            String latexContent) {
        createAndShowEditPopup(placeHolderElement, latexContent);
    }

    private void createAndShowEditPopup(final Element targetElement,
            String latexContent) {
        getMathPopup().hide();

        getMathPopup().setContents(latexContent);
        getMathPopup().setButtonText("Apply");
        getMathPopup().setCallback(new MathPopup.Callback() {
            public void aswerIsYes(boolean yes) {
                if (yes) {
                    String latex = getMathPopup().getLatexValue();

                    Element placeholder = createLatexPlaceholder(latex);
                    replaceElementInEditor(targetElement, placeholder);
                    textArea.setFocus(true);
                }
            }

        });
        getMathPopup().setPopupPositionAndShow(
                new PopupPanel.PositionCallback() {

                    public void setPosition(int offsetWidth, int offsetHeight) {
                        int trueAbsoluteLeft = targetElement.getAbsoluteLeft()
                                + textArea.getAbsoluteLeft();

                        int trueAbsoluteTop = targetElement.getAbsoluteTop()
                                + textArea.getAbsoluteTop();

                        int elementHorizontalCenter = trueAbsoluteLeft
                                + targetElement.getOffsetWidth() / 2;

                        int elementVerticalCenter = trueAbsoluteTop
                                + targetElement.getOffsetHeight() / 2;

                        int left = elementHorizontalCenter - offsetWidth / 2;
                        int top = elementVerticalCenter - offsetHeight / 2;

                        getMathPopup().setPopupPosition(left, top);
                    }
                });

    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == getRichTextToolbar().getMathifyButton()) {
            RichTextJs.getSelection(textArea.getElement());
            createAndShowAddPopup(event);
        } else {
            super.onClick(event);
        }
    }

    private void createAndShowAddPopup(ClickEvent event) {
        getMathPopup().hide();
        getMathPopup().setContents("");

        getMathPopup().showRelativeTo((UIObject) event.getSource());

        getMathPopup().setButtonText("Add");

        getMathPopup().setCallback(new MathPopup.Callback() {
            public void aswerIsYes(boolean yes) {
                if (yes) {
                    String latex = getMathPopup().getLatexValue();

                    Element placeholder = createLatexPlaceholder(latex);
                    getTextArea().getFormatter().insertHTML(
                            "<span id='latexPlaceHolderMarker' />");
                    Element markerElement = RichTextJs.getDocumentElement(
                            getTextArea().getElement()).getElementById(
                            "latexPlaceHolderMarker");

                    replaceElementInEditor(markerElement, placeholder);
                }
            }
        });
    }

    @Override
    public VRichMathAreaToolbar getRichTextToolbar() {
        return (VRichMathAreaToolbar) super.getRichTextToolbar();
    }

    public RichTextArea getTextArea() {
        return textArea;
    }

    public void reAttachClickHandlers() {
        JsArray<Element> mathImageElements = RichTextJs.getMathImageElements();
        for (int i = 0; i < mathImageElements.length(); i++) {
            Element jqueryElement = mathImageElements.get(i);
            Element element = RichTextJs.getDocumentElement(
                    getTextArea().getElement()).getElementById(
                    jqueryElement.getId());
            com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) element;
            final String latexContent = DOM.getElementProperty(castElement,
                    "title");

            // just attaching a click handler doesn't work here, we have to
            // recreate the whole element
            Element newPlaceHolder = createLatexPlaceholder(latexContent);
            replaceElementInEditor(element, newPlaceHolder);
        }
    }

    protected void replaceElementInEditor(final Element targetElement,
            Element placeholder) {
        Element mathAreaBody = RichTextJs.getBodyElement(textArea.getElement());
        DOM.insertBefore((com.google.gwt.user.client.Element) mathAreaBody,
                (com.google.gwt.user.client.Element) placeholder,
                (com.google.gwt.user.client.Element) targetElement);
        DOM.removeChild((com.google.gwt.user.client.Element) mathAreaBody,
                (com.google.gwt.user.client.Element) targetElement);
    }

    public MathPopup getMathPopup() {
        return mathPopup;
    }

}
