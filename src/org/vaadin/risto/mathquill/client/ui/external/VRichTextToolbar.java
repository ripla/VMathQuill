/*
 * Copyright 2011 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.risto.mathquill.client.ui.external;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * A modified version of sample toolbar for use with {@link RichTextArea}. It
 * provides a simple UI for all rich text formatting, dynamically displayed only
 * for the available functionality.
 */
// copied due private variables
public class VRichTextToolbar extends Composite {

    static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
            RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
            RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE };

    protected final Images images = createImageBundle();

    protected final Strings strings = createStringBundle();

    private VRichTextAreaEventHandler handler;

    private RichTextArea richText;
    @SuppressWarnings("deprecation")
    private RichTextArea.BasicFormatter basic;
    @SuppressWarnings("deprecation")
    private RichTextArea.ExtendedFormatter extended;

    protected final FlowPanel outer = new FlowPanel();
    protected final FlowPanel topPanel = new FlowPanel();
    protected final FlowPanel bottomPanel = new FlowPanel();
    protected ToggleButton bold;
    protected ToggleButton italic;
    protected ToggleButton underline;
    protected ToggleButton subscript;
    protected ToggleButton superscript;
    protected ToggleButton strikethrough;
    protected PushButton indent;
    protected PushButton outdent;
    protected PushButton justifyLeft;
    protected PushButton justifyCenter;
    protected PushButton justifyRight;
    protected PushButton hr;
    protected PushButton ol;
    protected PushButton ul;
    protected PushButton insertImage;
    protected PushButton createLink;
    protected PushButton removeLink;
    protected PushButton removeFormat;

    protected ListBox backColors;
    protected ListBox foreColors;
    protected ListBox fonts;
    protected ListBox fontSizes;

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText
     *            the rich text area to be controlled
     */
    @SuppressWarnings("deprecation")
    public VRichTextToolbar(RichTextArea richText) {
        this.setRichText(richText);
        setBasic(richText.getBasicFormatter());
        setExtended(richText.getExtendedFormatter());
        setHandler(createEventHandler());
        outer.add(topPanel);
        outer.add(bottomPanel);
        topPanel.setStyleName("gwt-RichTextToolbar-top");
        bottomPanel.setStyleName("gwt-RichTextToolbar-bottom");

        initWidget(outer);
        setStyleName("gwt-RichTextToolbar");

        if (getBasic() != null) {
            createBasicTopPanel(topPanel);
        }

        if (getExtended() != null) {
            createExtendedTopPanel(topPanel);
        }

        if (getBasic() != null) {
            createBasicBottomPanel(bottomPanel);

            // We only use these handlers for updating status, so don't hook
            // them up unless at least basic editing is supported.
            richText.addKeyUpHandler(getHandler());
            richText.addClickHandler(getHandler());
        }
    }

    protected VRichTextAreaEventHandler createEventHandler() {
        return new VRichTextAreaEventHandler(this);
    }

    protected void createBasicBottomPanel(FlowPanel bottomPanel) {
        bottomPanel.add(backColors = createColorList("Background"));
        bottomPanel.add(foreColors = createColorList("Foreground"));
        bottomPanel.add(fonts = createFontList());
        bottomPanel.add(fontSizes = createFontSizes());
    }

    protected void createExtendedTopPanel(FlowPanel topPanel) {
        topPanel.add(strikethrough = createToggleButton(images.strikeThrough(),
                strings.strikeThrough()));
        topPanel.add(indent = createPushButton(images.indent(),
                strings.indent()));
        topPanel.add(outdent = createPushButton(images.outdent(),
                strings.outdent()));
        topPanel.add(hr = createPushButton(images.hr(), strings.hr()));
        topPanel.add(ol = createPushButton(images.ol(), strings.ol()));
        topPanel.add(ul = createPushButton(images.ul(), strings.ul()));
        topPanel.add(insertImage = createPushButton(images.insertImage(),
                strings.insertImage()));
        topPanel.add(createLink = createPushButton(images.createLink(),
                strings.createLink()));
        topPanel.add(removeLink = createPushButton(images.removeLink(),
                strings.removeLink()));
        topPanel.add(removeFormat = createPushButton(images.removeFormat(),
                strings.removeFormat()));
    }

    protected void createBasicTopPanel(FlowPanel topPanel) {
        topPanel.add(bold = createToggleButton(images.bold(), strings.bold()));
        topPanel.add(italic = createToggleButton(images.italic(),
                strings.italic()));
        topPanel.add(underline = createToggleButton(images.underline(),
                strings.underline()));
        topPanel.add(subscript = createToggleButton(images.subscript(),
                strings.subscript()));
        topPanel.add(superscript = createToggleButton(images.superscript(),
                strings.superscript()));
        topPanel.add(justifyLeft = createPushButton(images.justifyLeft(),
                strings.justifyLeft()));
        topPanel.add(justifyCenter = createPushButton(images.justifyCenter(),
                strings.justifyCenter()));
        topPanel.add(justifyRight = createPushButton(images.justifyRight(),
                strings.justifyRight()));
    }

    protected Images createImageBundle() {
        return (Images) GWT.create(Images.class);
    }

    protected Strings createStringBundle() {
        return (Strings) GWT.create(Strings.class);
    }

    protected ListBox createColorList(String caption) {
        final ListBox lb = new ListBox();
        lb.addChangeHandler(getHandler());
        lb.setVisibleItemCount(1);

        lb.addItem(caption);
        lb.addItem(strings.white(), "white");
        lb.addItem(strings.black(), "black");
        lb.addItem(strings.red(), "red");
        lb.addItem(strings.green(), "green");
        lb.addItem(strings.yellow(), "yellow");
        lb.addItem(strings.blue(), "blue");
        lb.setTabIndex(-1);
        return lb;
    }

    protected ListBox createFontList() {
        final ListBox lb = new ListBox();
        lb.addChangeHandler(getHandler());
        lb.setVisibleItemCount(1);

        lb.addItem(strings.font(), "");
        lb.addItem(strings.normal(), "inherit");
        lb.addItem("Times New Roman", "Times New Roman");
        lb.addItem("Arial", "Arial");
        lb.addItem("Courier New", "Courier New");
        lb.addItem("Georgia", "Georgia");
        lb.addItem("Trebuchet", "Trebuchet");
        lb.addItem("Verdana", "Verdana");
        lb.setTabIndex(-1);
        return lb;
    }

    protected ListBox createFontSizes() {
        final ListBox lb = new ListBox();
        lb.addChangeHandler(getHandler());
        lb.setVisibleItemCount(1);

        lb.addItem(strings.size());
        lb.addItem(strings.xxsmall());
        lb.addItem(strings.xsmall());
        lb.addItem(strings.small());
        lb.addItem(strings.medium());
        lb.addItem(strings.large());
        lb.addItem(strings.xlarge());
        lb.addItem(strings.xxlarge());
        lb.setTabIndex(-1);
        return lb;
    }

    protected PushButton createPushButton(ImageResource img, String tip) {
        final PushButton pb = new PushButton(new Image(img));
        pb.addClickHandler(getHandler());
        pb.setTitle(tip);
        pb.setTabIndex(-1);
        return pb;
    }

    protected ToggleButton createToggleButton(ImageResource img, String tip) {
        final ToggleButton tb = new ToggleButton(new Image(img));
        tb.addClickHandler(getHandler());
        tb.setTitle(tip);
        tb.setTabIndex(-1);
        return tb;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    @SuppressWarnings("deprecation")
    void updateStatus() {
        if (getBasic() != null) {
            bold.setDown(getBasic().isBold());
            italic.setDown(getBasic().isItalic());
            underline.setDown(getBasic().isUnderlined());
            subscript.setDown(getBasic().isSubscript());
            superscript.setDown(getBasic().isSuperscript());
        }

        if (getExtended() != null) {
            strikethrough.setDown(getExtended().isStrikethrough());
        }
    }

    @SuppressWarnings("deprecation")
    public RichTextArea.BasicFormatter getBasic() {
        return basic;
    }

    @SuppressWarnings("deprecation")
    public void setBasic(RichTextArea.BasicFormatter basic) {
        this.basic = basic;
    }

    @SuppressWarnings("deprecation")
    public RichTextArea.ExtendedFormatter getExtended() {
        return extended;
    }

    @SuppressWarnings("deprecation")
    public void setExtended(RichTextArea.ExtendedFormatter extended) {
        this.extended = extended;
    }

    public RichTextArea getRichText() {
        return richText;
    }

    public void setRichText(RichTextArea richText) {
        this.richText = richText;
    }

    public VRichTextAreaEventHandler getHandler() {
        return handler;
    }

    public void setHandler(VRichTextAreaEventHandler handler) {
        this.handler = handler;
    }
}
