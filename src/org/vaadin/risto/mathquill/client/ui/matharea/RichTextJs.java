package org.vaadin.risto.mathquill.client.ui.matharea;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class RichTextJs {

    public static native Element getBodyElement(Element richText) /*-{
                                                                  return richText.contentWindow.document.body;
                                                                  }-*/;

    public static native Document getDocumentElement(Element richText) /*-{
                                                                       return richText.contentWindow.document;
                                                                       }-*/;

    public static native void disableObjectResizing(Element richText) /*-{
                                                                      richText.contentWindow.document.execCommand("enableObjectResizing", false, false);
                                                                      }-*/;

    public static native void disableObjectResizingIE(Element image) /*-{
                                                                     image.attachEvent("onresizestart", function(e) { e.returnValue = false; }, false);
                                                                     }-*/;

    public static native JsArray<Element> getMathImageElements() /*-{
                                                                  return $wnd.$('.latexPlaceHolder').get();
                                                                 }-*/;
}
