package org.vaadin.risto.mathquill;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class MathQuillDemoApplication extends Application {

    private static final long serialVersionUID = 2055422795751929484L;

    private Window globalToolbarWindow = null;

    @Override
    public void init() {
        setTheme(Reindeer.THEME_NAME);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setHeight("100%");

        Component demoLayout = buildDemoLayout();
        mainLayout.addComponent(demoLayout);
        demoLayout.setWidth("768px");
        demoLayout.setHeight("100%");
        mainLayout.setComponentAlignment(demoLayout, Alignment.TOP_CENTER);

        Window mainWindow = new Window("MathQuill Vaadin integration demo");
        mainWindow.setContent(mainLayout);

        setMainWindow(mainWindow);
    }

    protected Window createGlobalToolbarWindow(GlobalMathToolbar floatingToolbar) {
        Window toolbarWindow = new Window("Floating toolbar");
        toolbarWindow.setStyleName(Reindeer.WINDOW_LIGHT);
        toolbarWindow.addComponent(floatingToolbar);
        return toolbarWindow;
    }

    protected ClickListener createToolbarListener(
            final GlobalMathToolbar floatingToolbar) {
        return new Button.ClickListener() {

            private static final long serialVersionUID = -4972252645127891269L;

            public void buttonClick(ClickEvent event) {
                getMainWindow().removeWindow(globalToolbarWindow);
                globalToolbarWindow = createGlobalToolbarWindow(floatingToolbar);
                getMainWindow().addWindow(globalToolbarWindow);
            }
        };
    }

    private Component buildDemoLayout() {
        Panel demoPanel = new Panel();
        ((AbstractOrderedLayout) demoPanel.getContent()).setSpacing(true);
        demoPanel.setHeight("100%");

        Component mathLabelDemo = buildMathLabelDemo();
        Component mathTextFieldDemo = buildMathTextFieldDemo();
        Component mathElementBarDemo = buildMathElementBarDemo();
        Component richMathEditorDemo = buildRichMathEditorDemo();

        demoPanel.addComponent(mathLabelDemo);
        demoPanel.addComponent(createDivider());
        demoPanel.addComponent(mathTextFieldDemo);
        demoPanel.addComponent(createDivider());
        demoPanel.addComponent(mathElementBarDemo);
        demoPanel.addComponent(createDivider());
        demoPanel.addComponent(richMathEditorDemo);

        VerticalLayout demoLayout = createBasicDemoContainer();
        demoLayout.setSizeFull();

        demoLayout.addComponent(buildGenericHeader());
        demoLayout.addComponent(demoPanel);
        demoLayout.setExpandRatio(demoPanel, 1.0f);

        return demoLayout;
    }

    private Component buildGenericHeader() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setMargin(true);

        Label genericHeaderLabel = new Label("VMathQuill demo");
        genericHeaderLabel.addStyleName(Reindeer.LABEL_H1);
        Label genericHeaderContent = new Label(
                "VMathQuill is a Vaadin integration of the <a href=\"http://mathquill.github.com\">MathQuill</a> javascript library. The integration sources can be found <a href=\"https://github.com/ripla/VMathQuill\">here</a>");
        genericHeaderContent.setContentMode(Label.CONTENT_XHTML);

        layout.addComponent(genericHeaderLabel);
        layout.addComponent(genericHeaderContent);

        Panel panel = new Panel();
        panel.setContent(layout);
        return panel;
    }

    private Component createDivider() {
        return new Label("<hr/>", Label.CONTENT_XHTML);
    }

    private Component buildMathLabelDemo() {
        ObjectProperty<String> exampleDatasource = new ObjectProperty<String>(
                "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");

        Label mathLabelHeader = new Label("MathLabel");
        mathLabelHeader.setStyleName(Reindeer.LABEL_H1);
        Label firstExampleHeader = new Label("Example");
        firstExampleHeader.setStyleName(Reindeer.LABEL_H2);
        TextArea firstExampleSource = new TextArea("Label contents",
                exampleDatasource);
        firstExampleSource.setImmediate(true);
        MathLabel firstExample = new MathLabel(exampleDatasource);

        HorizontalLayout firstExampleLayout = new HorizontalLayout();
        firstExampleLayout.setWidth("70%");
        firstExampleLayout.addComponent(firstExampleSource);
        firstExampleLayout.addComponent(firstExample);

        VerticalLayout layout = createBasicDemoContainer();

        layout.addComponent(mathLabelHeader);
        layout.addComponent(new Label(
                "MathLabel integrates MathQuill as a Vaadin Label, enabling users to display static texts as beautiful math."));
        layout.addComponent(firstExampleHeader);
        layout.addComponent(firstExampleLayout);

        return layout;
    }

    private VerticalLayout createBasicDemoContainer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);
        layout.setMargin(true, false, false, false);
        layout.setHeight("100%");
        return layout;
    }

    private Component buildMathTextFieldDemo() {
        Label mathLabelHeader = new Label("MathTextField");
        mathLabelHeader.setStyleName(Reindeer.LABEL_H1);

        Label firstExampleHeader = new Label("Example");
        Label secondExampleHeader = new Label("Example, mixed mode");
        firstExampleHeader.setStyleName(Reindeer.LABEL_H2);
        secondExampleHeader.setStyleName(Reindeer.LABEL_H2);

        Component normalExample = createMathTextFieldExample(false);
        Component mixedModeExample = createMathTextFieldExample(true);

        VerticalLayout content = createBasicDemoContainer();

        content.addComponent(mathLabelHeader);
        content.addComponent(new Label(
                "MathTextField integrates MathQuills editable math textbox, enabling users to display and edit math."));
        content.addComponent(firstExampleHeader);
        content.addComponent(normalExample);
        content.addComponent(secondExampleHeader);
        content.addComponent(new Label(
                "When in mixed mode, MathTextField renders math only when its place between $ signs"));
        content.addComponent(mixedModeExample);

        return content;
    }

    private Component buildMathElementBarDemo() {
        Label mathElementHeader = new Label("Adding math elements");
        mathElementHeader.setStyleName(Reindeer.LABEL_H1);

        final MathTextField targetField = new MathTextField();
        targetField.setWidth("100%");

        HorizontalLayout buttons = new HorizontalLayout();
        GlobalMathToolbar floatingToolbar = new GlobalMathToolbar();

        for (final DemoMathElement element : DemoMathElement.values()) {
            // static math element bar
            Button addElement = new Button(element.html);
            addElement.addListener(new Button.ClickListener() {

                private static final long serialVersionUID = 8684915066987587726L;

                public void buttonClick(ClickEvent event) {
                    targetField.addMathElement(element.latex);
                }
            });
            buttons.addComponent(addElement);

            // floating math element bar
            floatingToolbar.addButton(element.html, element.latex);
        }

        VerticalLayout buttonsAndField = new VerticalLayout();
        buttonsAndField.addComponent(buttons);
        buttonsAndField.addComponent(targetField);

        Label staticToolbarHeader = new Label("User created toolbar");
        staticToolbarHeader.setStyleName(Reindeer.LABEL_H2);
        Label staticToolbarText = new Label(
                "This toolbar is a normal Vaadin HorizontalLayout. It specifically targets this MathTextField. It is meant to be a simple and themable as possible.");

        Label globalToolbarHeader = new Label("Floating toolbar");
        globalToolbarHeader.setStyleName(Reindeer.LABEL_H2);
        Label globalToolbarText = new Label(
                "This is a custom client side toolbar. It can target any focused math field.");

        Button openGlobalToolbar = new Button("Open floating toolbar");
        openGlobalToolbar.addListener(createToolbarListener(floatingToolbar));
        VerticalLayout layout = createBasicDemoContainer();
        layout.addComponent(mathElementHeader);
        layout.addComponent(new Label(
                "You can also add predefined math elements to a MathTextField. Try adding elements with and without selecting text first."));
        layout.addComponent(staticToolbarHeader);
        layout.addComponent(staticToolbarText);
        layout.addComponent(buttonsAndField);
        layout.addComponent(globalToolbarHeader);
        layout.addComponent(globalToolbarText);
        layout.addComponent(openGlobalToolbar);
        return layout;
    }

    private Component buildRichMathEditorDemo() {
        ObjectProperty<String> exampleDatasource = new ObjectProperty<String>(
                "");

        Label mathElementHeader = new Label("RichTextEditor with math support");
        mathElementHeader.setStyleName(Reindeer.LABEL_H1);

        final MathLabel mathContent = new MathLabel();

        Label mathContentCaption = new Label("MathLabel");
        mathContentCaption.setStyleName(Reindeer.LABEL_H2);

        Label normalLabel = new Label();
        normalLabel.setPropertyDataSource(exampleDatasource);
        normalLabel.setContentMode(Label.CONTENT_XHTML);

        Label normalLabelCaption = new Label("Vaadin Label, XHTML mode");
        normalLabelCaption.setStyleName(Reindeer.LABEL_H2);

        Label layoutCaption = new Label("Layout from the MathArea contents");
        layoutCaption.setStyleName(Reindeer.LABEL_H2);

        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);

        final RichMathArea richEditor = new RichMathArea();
        richEditor.setImmediate(true);
        richEditor.setPropertyDataSource(exampleDatasource);

        exampleDatasource.addListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 6852597702928266038L;

            public void valueChange(ValueChangeEvent event) {
                mathContent.setValue(MathValueParser.get().getMathValue(
                        richEditor));
                contentLayout.removeAllComponents();

                for (Component c : MathValueParser.get()
                        .getMathValueAsComponents(richEditor)) {
                    contentLayout.addComponent(c);
                }
            }
        });

        VerticalLayout layout = createBasicDemoContainer();
        layout.addComponent(mathElementHeader);
        layout.addComponent(new Label(
                "Vaadin RichTextArea with embedded MathQuill support."));
        layout.addComponent(richEditor);
        layout.addComponent(mathContentCaption);
        layout.addComponent(mathContent);
        layout.addComponent(normalLabelCaption);
        layout.addComponent(normalLabel);
        layout.addComponent(layoutCaption);
        layout.addComponent(contentLayout);

        return layout;
    }

    private Component createMathTextFieldExample(boolean isMixedMode) {
        ObjectProperty<String> exampleDatasource;
        if (isMixedMode) {
            exampleDatasource = new ObjectProperty<String>(
                    "Solution for the quadratic equation $\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}$ Cool, right?");
        } else {
            exampleDatasource = new ObjectProperty<String>(
                    "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");
        }
        final TextArea exampleSource = new TextArea("Field contents",
                exampleDatasource);
        exampleSource.setImmediate(true);

        String mathTextFieldCaption = isMixedMode ? "Mixed mode MathTextField"
                : "Normal MathTextField";
        MathTextField example = new MathTextField(mathTextFieldCaption,
                exampleDatasource);
        example.setMixedMode(isMixedMode);

        HorizontalLayout exampleLayout = new HorizontalLayout();
        exampleLayout.setWidth("80%");
        exampleLayout.addComponent(exampleSource);
        exampleLayout.addComponent(example);

        // work around a bug
        exampleDatasource.addListener(new Property.ValueChangeListener() {

            private static final long serialVersionUID = 2198460842375047971L;

            public void valueChange(ValueChangeEvent event) {
                exampleSource.requestRepaint();
            }
        });
        return exampleLayout;
    }
}
