package org.vaadin.risto.mathquill;

import com.vaadin.Application;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
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

        Component demoLayout = buildDemoLayout();
        mainLayout.addComponent(demoLayout);
        demoLayout.setWidth("768px");
        mainLayout.setComponentAlignment(demoLayout, Alignment.TOP_CENTER);

        Window mainWindow = new Window("MathQuill Vaadin integration demo");
        mainWindow.setContent(mainLayout);

        setMainWindow(mainWindow);
    }

    private Component buildDemoLayout() {
        VerticalLayout demoLayout = new VerticalLayout();
        demoLayout.setSpacing(true);

        Component mathLabelDemo = buildMathLabelDemo();
        Component mathTextFieldDemo = buildMathTextFieldDemo();

        demoLayout.addComponent(mathLabelDemo);
        demoLayout.addComponent(mathTextFieldDemo);

        return demoLayout;
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

        Panel panel = new Panel();
        panel.setWidth("100%");
        ((AbstractOrderedLayout) panel.getContent()).setSpacing(true);

        panel.addComponent(mathLabelHeader);
        panel.addComponent(new Label(
                "MathLabel integrates MathQuill as a Vaadin Label, enabling users to display static texts as beautiful math."));
        panel.addComponent(firstExampleHeader);
        panel.addComponent(firstExampleLayout);

        return panel;
    }

    private Component buildMathTextFieldDemo() {
        Label mathLabelHeader = new Label("MathTextField");
        mathLabelHeader.setStyleName(Reindeer.LABEL_H1);

        Label firstExampleHeader = new Label("Example");
        Label secondExampleHeader = new Label("Example, mixed mode");
        firstExampleHeader.setStyleName(Reindeer.LABEL_H2);
        secondExampleHeader.setStyleName(Reindeer.LABEL_H2);

        HorizontalLayout normalExample = createMathTextFieldExample(false);
        HorizontalLayout mixedModeExample = createMathTextFieldExample(true);

        Panel panel = new Panel("");
        panel.setWidth("100%");
        ((AbstractOrderedLayout) panel.getContent()).setSpacing(true);

        panel.addComponent(mathLabelHeader);
        panel.addComponent(new Label(
                "MathTextField integrates MathQuills editable math textbox, enabling users to display and edit math."));
        panel.addComponent(firstExampleHeader);
        panel.addComponent(normalExample);
        panel.addComponent(secondExampleHeader);
        panel.addComponent(new Label(
                "When in mixed mode, MathTextField renders math only when its place between $ signs"));
        panel.addComponent(mixedModeExample);

        return panel;
    }

    private HorizontalLayout createMathTextFieldExample(boolean isMixedMode) {
        ObjectProperty<String> exampleDatasource;
        if (isMixedMode) {
            exampleDatasource = new ObjectProperty<String>(
                    "Solution for the quadratic equation $\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}$ Cool, right?");
        } else {
            exampleDatasource = new ObjectProperty<String>(
                    "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");
        }
        TextField firstExampleSource = new TextField("Field contents",
                exampleDatasource);
        firstExampleSource.setImmediate(true);

        String mathTextFieldCaption = isMixedMode ? "Mixed mode MathTextField"
                : "Normal MathTextField";
        MathTextField firstExample = new MathTextField(mathTextFieldCaption,
                exampleDatasource);
        firstExample.setMixedMode(isMixedMode);

        HorizontalLayout firstExampleLayout = new HorizontalLayout();
        firstExampleLayout.setWidth("80%");
        firstExampleLayout.addComponent(firstExampleSource);
        firstExampleLayout.addComponent(firstExample);
        return firstExampleLayout;
    }
}
