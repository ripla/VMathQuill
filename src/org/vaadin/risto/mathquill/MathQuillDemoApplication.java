package org.vaadin.risto.mathquill;

import com.vaadin.Application;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
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

        demoLayout.addComponent(mathLabelDemo);

        return demoLayout;
    }

    private Component buildMathLabelDemo() {
        Label firstExampleHeader = new Label("Examples");
        Label firstExampleNormal = new Label(
                "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");
        firstExampleHeader.setStyleName(Reindeer.LABEL_H2);
        MathLabel firstExample = new MathLabel(
                "\\frac{-b\\pm \\sqrt{b^2-4ac}}{2a}");

        HorizontalLayout firstExampleLayout = new HorizontalLayout();
        firstExampleLayout.addComponent(firstExampleNormal);
        firstExampleLayout.addComponent(firstExample);

        Panel panel = new Panel("MathLabel");
        ((AbstractOrderedLayout) panel.getContent()).setSpacing(true);
        panel.addComponent(new Label(
                "MathLabel integrates MathQuill as a Vaadin Label, enabling users to display static texts as beautiful math."));
        panel.addComponent(firstExampleHeader);
        panel.addComponent(firstExample);

        return panel;
    }
}
