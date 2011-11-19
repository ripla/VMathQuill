package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NodeList;
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

    private static final String LATEX_PLACE_HOLDER_MARKER = "latexPlaceHolderMarker";
    private final RichTextArea textArea;
    private final MathPopup mathPopup;

    public MathEventHandler(VRichMathAreaToolbar toolbar,
            final RichTextArea mathArea) {
        super(toolbar);
        this.textArea = mathArea;
        mathPopup = new MathPopup();
    }

    protected void setLatexImageProperties(final String latexContent,
            final Element latexPlaceHolder) {
        com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) latexPlaceHolder;

        // create image URL
        String src = URL.encode("http://latex.codecogs.com/gif.latex?"
                + latexContent);

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
    }

    protected void handlePlaceHolderClick(Element placeHolderElement,
            String latexContent) {
        createAndShowEditPopup(placeHolderElement, latexContent);
    }

    protected void handleAddButtonClick(ClickEvent event) {
        // work around a bug: at least gecko throws an exception if the
        // editable area hasn't been focused first
        textArea.setFocus(true);
        createAndShowAddPopup(event);
    }

    private void createAndShowEditPopup(final Element targetElement,
            String latexContent) {
        getMathPopup().hide();

        getMathPopup().setContents(latexContent);
        getMathPopup().setButtonText("Apply");
        getMathPopup().setCallback(new MathPopup.Callback() {
            public void aswerIsYes(boolean yes) {
                if (yes) {
                    String newLatex = getMathPopup().getLatexValue();
                    setLatexImageProperties(newLatex, targetElement);
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
            handleAddButtonClick(event);
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
                    insertNewLatex(latex);
                    textArea.setFocus(true);
                }
            }

        });
    }

    protected void insertNewLatex(String latex) {
        ImageElement marker = insertMarker();
        setLatexImageProperties(latex, marker);
    }

    protected ImageElement insertMarker() {
        getTextArea().getFormatter().insertImage(LATEX_PLACE_HOLDER_MARKER);
        return getMarkerElement();
    }

    protected ImageElement getMarkerElement() {
        NodeList<Element> elements = RichTextJs.getDocumentElement(
                getTextArea().getElement()).getElementsByTagName("img");
        return filterImageElementWithSrc(elements, LATEX_PLACE_HOLDER_MARKER);
    }

    protected ImageElement filterImageElementWithSrc(
            NodeList<Element> elements, String wantedSrc) {
        for (int i = 0; i < elements.getLength(); i++) {
            ImageElement element = (ImageElement) elements.getItem(i);
            if (element.getSrc() != null
                    && element.getSrc().endsWith(LATEX_PLACE_HOLDER_MARKER)) {
                return element;
            }
        }
        throw new IllegalStateException("No img tag with given src "
                + wantedSrc);
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
            Element oldImagePlaceholder = RichTextJs.getDocumentElement(
                    getTextArea().getElement()).getElementById(
                    jqueryElement.getId());
            com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) oldImagePlaceholder;
            final String latexContent = DOM.getElementProperty(castElement,
                    "title");

            setLatexImageProperties(latexContent, oldImagePlaceholder);
        }
    }

    public MathPopup getMathPopup() {
        return mathPopup;
    }
}
