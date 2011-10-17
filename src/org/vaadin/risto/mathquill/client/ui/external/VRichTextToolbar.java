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

    private final Images images = createImageBundle();

    private final Strings strings = createStringBundle();

    private final EventHandler handler = new EventHandler(this);

    final RichTextArea richText;
    @SuppressWarnings("deprecation")
    final RichTextArea.BasicFormatter basic;
    @SuppressWarnings("deprecation")
    final RichTextArea.ExtendedFormatter extended;

    private final FlowPanel outer = new FlowPanel();
    private final FlowPanel topPanel = new FlowPanel();
    private final FlowPanel bottomPanel = new FlowPanel();
    ToggleButton bold;
    ToggleButton italic;
    ToggleButton underline;
    ToggleButton subscript;
    ToggleButton superscript;
    ToggleButton strikethrough;
    PushButton indent;
    PushButton outdent;
    PushButton justifyLeft;
    PushButton justifyCenter;
    PushButton justifyRight;
    PushButton hr;
    PushButton ol;
    PushButton ul;
    PushButton insertImage;
    PushButton createLink;
    PushButton removeLink;
    PushButton removeFormat;

    ListBox backColors;
    ListBox foreColors;
    ListBox fonts;
    ListBox fontSizes;

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText
     *            the rich text area to be controlled
     */
    @SuppressWarnings("deprecation")
    public VRichTextToolbar(RichTextArea richText) {
        this.richText = richText;
        basic = richText.getBasicFormatter();
        extended = richText.getExtendedFormatter();

        outer.add(topPanel);
        outer.add(bottomPanel);
        topPanel.setStyleName("gwt-RichTextToolbar-top");
        bottomPanel.setStyleName("gwt-RichTextToolbar-bottom");

        initWidget(outer);
        setStyleName("gwt-RichTextToolbar");

        if (basic != null) {
            createBasicTopPanel();
        }

        if (extended != null) {
            createExtendedTopPanel();
        }

        if (basic != null) {
            createBasicBottomPanel(richText);
        }
    }

    protected void createBasicBottomPanel(RichTextArea richText) {
        bottomPanel.add(backColors = createColorList("Background"));
        bottomPanel.add(foreColors = createColorList("Foreground"));
        bottomPanel.add(fonts = createFontList());
        bottomPanel.add(fontSizes = createFontSizes());

        // We only use these handlers for updating status, so don't hook
        // them up unless at least basic editing is supported.
        richText.addKeyUpHandler(handler);
        richText.addClickHandler(handler);
    }

    protected void createExtendedTopPanel() {
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

    protected void createBasicTopPanel() {
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
        lb.addChangeHandler(handler);
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
        lb.addChangeHandler(handler);
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
        lb.addChangeHandler(handler);
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
        pb.addClickHandler(handler);
        pb.setTitle(tip);
        pb.setTabIndex(-1);
        return pb;
    }

    protected ToggleButton createToggleButton(ImageResource img, String tip) {
        final ToggleButton tb = new ToggleButton(new Image(img));
        tb.addClickHandler(handler);
        tb.setTitle(tip);
        tb.setTabIndex(-1);
        return tb;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    @SuppressWarnings("deprecation")
    void updateStatus() {
        if (basic != null) {
            bold.setDown(basic.isBold());
            italic.setDown(basic.isItalic());
            underline.setDown(basic.isUnderlined());
            subscript.setDown(basic.isSubscript());
            superscript.setDown(basic.isSuperscript());
        }

        if (extended != null) {
            strikethrough.setDown(extended.isStrikethrough());
        }
    }
}
