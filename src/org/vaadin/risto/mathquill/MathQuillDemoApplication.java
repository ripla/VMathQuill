package org.vaadin.risto.mathquill;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class MathQuillDemoApplication extends Application {

    private static final long serialVersionUID = 2055422795751929484L;

    @Override
    public void init() {
        setTheme(Reindeer.THEME_NAME);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
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

    private Component buildDemoLayout() {
        Panel demoLayout = new Panel();
        ((AbstractOrderedLayout) demoLayout.getContent()).setSpacing(true);

        Component mathLabelDemo = buildMathLabelDemo();
        Component mathTextFieldDemo = buildMathTextFieldDemo();
        Component mathElementBarDemo = buildMathElementBarDemo();
        Component richMathEditorDemo = buildRichMathEditorDemo();

        demoLayout.addComponent(mathLabelDemo);
        demoLayout.addComponent(createDivider());
        demoLayout.addComponent(mathTextFieldDemo);
        demoLayout.addComponent(createDivider());
        demoLayout.addComponent(mathElementBarDemo);
        demoLayout.addComponent(createDivider());
        demoLayout.addComponent(richMathEditorDemo);

        return demoLayout;
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
        TextField firstExampleSource = new TextField("Label contents",
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
        for (final DemoMathElement element : DemoMathElement.values()) {
            Button addElement = new Button(element.html);
            addElement.addListener(new Button.ClickListener() {

                private static final long serialVersionUID = 8684915066987587726L;

                public void buttonClick(ClickEvent event) {
                    targetField.addMathElement(element.element);
                }
            });
            buttons.addComponent(addElement);
        }

        VerticalLayout buttonsAndField = new VerticalLayout();
        buttonsAndField.addComponent(buttons);
        buttonsAndField.addComponent(targetField);

        VerticalLayout layout = createBasicDemoContainer();
        layout.addComponent(mathElementHeader);
        layout.addComponent(new Label(
                "You can also add predefined math elements to a MathTextField. Try adding elements with and without selecting text first."));
        layout.addComponent(buttonsAndField);

        return layout;
    }

    private Component buildRichMathEditorDemo() {
        Label mathElementHeader = new Label("RichTextEditor with math support");
        mathElementHeader.setStyleName(Reindeer.LABEL_H1);

        RichMathArea richEditor = new RichMathArea();

        VerticalLayout layout = createBasicDemoContainer();
        layout.addComponent(mathElementHeader);
        layout.addComponent(new Label(
                "Vaadin RichTextArea with embedded MathQuill support."));
        layout.addComponent(richEditor);

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
        final TextField exampleSource = new TextField("Field contents",
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
