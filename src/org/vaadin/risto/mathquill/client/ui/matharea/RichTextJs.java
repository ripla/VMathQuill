package org.vaadin.risto.mathquill.client.ui.matharea;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;

public class RichTextJs {

    public static native JsArrayString getSelection(Element elem) /*-{
                                                                  var txt = "";
                                                                  var pos = 0;
                                                                  if (elem.contentWindow.getSelection) {
                                                                  txt = elem.contentWindow.getSelection();
                                                                  pos = elem.contentWindow.getSelection().getRangeAt(0).startOffset;
                                                                  } else if (elem.contentWindow.document.getSelection) {
                                                                  txt = elem.contentWindow.document.getSelection();
                                                                  pos = elem.contentWindow.document.getSelection().getRangeAt(0).startOffset;
                                                                  } else if (elem.contentWindow.document.selection) {
                                                                  txt = elem.contentWindow.document.selection.createRange().text;
                                                                  pos = elem.contentWindow.document.selection.getRangeAt(0).startOffset;
                                                                  }
                                                                  return [""+txt,""+pos];
                                                                  }-*/;

    public static native void append(Element richText, Element newChild) /*-{
                                                                                  if(richText && newChild) {
                                                                                  richText.contentWindow.document.body.appendChild(newChild);
                                                                                  }
                                                                                  }-*/;

    public static native Element getBodyElement(Element richText) /*-{
                                                                   return richText.contentWindow.document.body;
                                                                   }-*/;

    public static native void disableObjectResizing(Element richText) /*-{
                                                                      richText.contentWindow.document.execCommand("enableObjectResizing", false, false);
                                                                      }-*/;

    public static native void disableObjectResizingIE(Element image) /*-{
                                                                     image.attachEvent("onresizestart", function(e) { e.returnValue = false; }, false);
                                                                     }-*/;
}
