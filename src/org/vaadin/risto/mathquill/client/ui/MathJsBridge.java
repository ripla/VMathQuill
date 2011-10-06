package org.vaadin.risto.mathquill.client.ui;

import com.google.gwt.dom.client.Element;

/**
 * Bridge between GWT and the MathQuill javascript library.
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * 
 */
public class MathJsBridge {

    /**
     * Mark the given element for MathQuill
     * 
     * @param e
     */
    public static native void makeMathElement(Element e) /*-{ 
                                                         $wnd.$(e).mathquill()
                                                         
                                                         }-*/;

    /**
     * Mark the given element as editable for MathQuill
     * 
     * @param e
     */
    public static native void mathifyEditable(Element e) /*-{ 
                                                         $wnd.$(e).mathquill('editable')
                                                         
                                                         }-*/;

    /**
     * Mark the given element as editable textbox for MathQuill
     * 
     * @param e
     */
    public static native void mathifyTextBox(Element e) /*-{ 
                                                         $wnd.$(e).mathquill('textbox')
                                                         
                                                         }-*/;

    /**
     * Update the contents of the given MathQuill element.
     * 
     * @param e
     * @param content
     */
    public static native void setMathContent(Element e, String content) /*-{ 
                                                                        $wnd.$(e).mathquill('latex', content)
                                                                        
                                                                        }-*/;

    /**
     * Redraw the given MathElement.
     * 
     * @param e
     */
    public static native void updateMath(Element e) /*-{ 
                                                    $wnd.$(e).mathquill('redraw')
                                                    
                                                    }-*/;
}
