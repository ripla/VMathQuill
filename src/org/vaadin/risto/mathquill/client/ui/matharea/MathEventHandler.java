package org.vaadin.risto.mathquill.client.ui.matharea;

import org.vaadin.risto.mathquill.client.ui.external.VRichTextAreaEventHandler;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.UIObject;

public class MathEventHandler extends VRichTextAreaEventHandler {

    private final RichTextArea textArea;
    private final MathPopup mathPopup;
    private boolean objectResizingDisabled;

    public MathEventHandler(VRichMathAreaToolbar toolbar,
            final RichTextArea mathArea) {
        super(toolbar);
        this.textArea = mathArea;
        mathPopup = new MathPopup();

        mathPopup.setCallback(new MathPopup.Callback() {
            public void aswerIsYes(boolean yes) {
                if (yes) {

                    String latex = mathPopup.getLatexValue();
                    // getTextArea().getFormatter().insertHTML(latex);

                    Element placeholder = createLatexPlaceholder(latex);
                    Element mathAreaBody = RichTextJs.getBodyElement(mathArea
                            .getElement());
                    // RichTextJs.disableObjectResizingIE(placeholder);
                    DOM.appendChild(
                            (com.google.gwt.user.client.Element) mathAreaBody,
                            (com.google.gwt.user.client.Element) placeholder);

                    // MathJsBridge.makeMathElement(span);
                    // MathJsBridge.setMathContent(span, latex);
                    // RichTextJs.append(textArea.getElement(), span);

                }
            }
        });
    }

    protected Element createLatexPlaceholder(String latexContent) {
        // create image
        String src = URL.encode("http://latex.codecogs.com/gif.latex?"
                + latexContent);
        // String styleSrc = "url(" + src + ")";

        // create element
        final Element latexPlaceHolder = DOM.createImg();
        // final Element latexPlaceHolder = DOM.createDiv();
        com.google.gwt.user.client.Element castElement = (com.google.gwt.user.client.Element) latexPlaceHolder;

        // set element clickhandler
        DOM.sinkEvents(castElement, Event.ONCLICK);
        DOM.setEventListener(castElement, new EventListener() {

            public void onBrowserEvent(Event event) {
                handlePlaceHolderClick(Element.as(event.getEventTarget()));

            }

        });

        // textArea.getFormatter().insertHTML(
        // "<div style=\"background-image: url(" + styleSrc + ")\" />");

        // set element content
        DOM.setElementProperty(castElement, "title", latexContent);
        // latexPlaceHolder.getStyle().setBackgroundImage(styleSrc);
        // latexPlaceHolder.getStyle().setDisplay(Display.INLINE_BLOCK);
        DOM.setImgSrc(castElement, src);

        // set element dimensions
        // ImagePreloader.load(src, new ImageLoadHandler() {
        //
        // public void imageLoaded(ImageLoadEvent event) {
        // Dimensions imageSize = event.getDimensions();
        // latexPlaceHolder.getStyle().setWidth(imageSize.getWidth(),
        // Unit.PX);
        // latexPlaceHolder.getStyle().setHeight(imageSize.getHeight(),
        // Unit.PX);
        // }
        // });

        // set element unique id

        return latexPlaceHolder;
    }

    protected void handlePlaceHolderClick(Element placeHolderElement) {
        Window.alert("yeah");

    }

    @Override
    public void onClick(ClickEvent event) {
        // this is a workaround for the slow attaching of the editable area
        // at this point we can be sure that everything is set up properly
        if (!objectResizingDisabled) {
            RichTextJs.disableObjectResizing(textArea.getElement());
            objectResizingDisabled = true;
        }

        if (event.getSource() == getRichTextToolbar().getMathifyButton()) {
            RichTextJs.getSelection(textArea.getElement());
            mathPopup.setContents("");

            mathPopup.showRelativeTo((UIObject) event.getSource());

            mathPopup.setButtonText("Add");
        } else {
            super.onClick(event);
        }
    }

    @Override
    public VRichMathAreaToolbar getRichTextToolbar() {
        return (VRichMathAreaToolbar) super.getRichTextToolbar();
    }

    public RichTextArea getTextArea() {
        return textArea;
    }

}
