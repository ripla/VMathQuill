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
        ObjectProperty<String> exampleDatasource = new ObjectProperty<String>(
                "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");

        Label mathLabelHeader = new Label("MathTextField");
        mathLabelHeader.setStyleName(Reindeer.LABEL_H1);
        Label firstExampleHeader = new Label("Example");
        TextField firstExampleSource = new TextField("Field contents",
                exampleDatasource);
        firstExampleSource.setImmediate(true);
        firstExampleHeader.setStyleName(Reindeer.LABEL_H2);
        MathTextField firstExample = new MathTextField("Normal MathTextField",
                exampleDatasource);

        HorizontalLayout firstExampleLayout = new HorizontalLayout();
        firstExampleLayout.setWidth("70%");
        firstExampleLayout.addComponent(firstExampleSource);
        firstExampleLayout.addComponent(firstExample);

        Panel panel = new Panel("");
        panel.setWidth("100%");
        ((AbstractOrderedLayout) panel.getContent()).setSpacing(true);

        panel.addComponent(mathLabelHeader);
        panel.addComponent(new Label(
                "MathTextField integrates MathQuills editable math textbox, enabling users to display and edit math."));
        panel.addComponent(firstExampleHeader);
        panel.addComponent(firstExampleLayout);

        return panel;
    }
}
