package org.vaadin.risto.mathquill.client.ui.matharea;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;

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
import com.vaadin.terminal.gwt.client.BrowserInfo;

public class MathEventHandler extends VRichTextAreaEventHandler {

    private static final String LATEX_PLACE_HOLDER = "latexPlaceHolder";
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
        latexPlaceHolder.setPropertyString("className", LATEX_PLACE_HOLDER);
        latexPlaceHolder.getStyle().setCursor(Cursor.POINTER);

        // set element unique id
        latexPlaceHolder.setId("latexPlaceHolder_" + latexContent + "_"
                + Math.random());
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
                    editLatex(targetElement, newLatex);
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

    protected void editLatex(final Element latexElement, String newLatex) {
        // if (BrowserInfo.get().isGecko()) {
        // final Element newLatexElement = createNewLatexImage(newLatex);
        // replaceElementInEditor(latexElement, newLatexElement);
        // } else {
        setLatexImageProperties(newLatex, latexElement);
        // }
    }

    protected Element createNewLatexImage(String newLatex) {
        final Element newLatexElement = DOM.createImg();
        setLatexImageProperties(newLatex, newLatexElement);
        return newLatexElement;
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
        if (BrowserInfo.get().isGecko()) {
            ImageElement marker = insertMarker();
            final Element newLatexElement = createNewLatexImage(latex);
            replaceElementInEditor(marker, newLatexElement);
        } else {
            ImageElement marker = insertMarker();
            setLatexImageProperties(latex, marker);
        }
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
        for (Element element : getLatexImageElements()) {
            com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) element;
            final String latexContent = DOM.getElementProperty(castElement,
                    "title");

            final Element newLatexElement = createNewLatexImage(latexContent);
            replaceElementInEditor(element, newLatexElement);
        }
    }

    private List<Element> getLatexImageElements() {
        NodeList<Element> elementsByTagName = RichTextJs.getBodyElement(
                getTextArea().getElement()).getElementsByTagName("img");
        List<Element> result = new ArrayList<Element>();
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Element item = elementsByTagName.getItem(i);
            if (LATEX_PLACE_HOLDER.equals(item.getClassName())) {
                result.add(item);
            }
        }
        return result;
    }

    public MathPopup getMathPopup() {
        return mathPopup;
    }

    protected void replaceElementInEditor(final Element oldElement,
            Element newElement) {
        Element mathAreaBody = RichTextJs.getBodyElement(textArea.getElement());
        DOM.insertBefore((com.google.gwt.user.client.Element) mathAreaBody,
                (com.google.gwt.user.client.Element) newElement,
                (com.google.gwt.user.client.Element) oldElement);
        DOM.removeChild((com.google.gwt.user.client.Element) mathAreaBody,
                (com.google.gwt.user.client.Element) oldElement);
    }
}
