package org.vaadin.risto.mathquill;

import java.util.List;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TestApplication extends Application {

    private static final long serialVersionUID = 3859882314481724059L;

    private RichMathArea mathArea;

    private RichMathArea mathArea2;

    private RichMathArea mathArea3;

    private CssLayout componentLayout;

    @Override
    public void init() {
        Window mainWindow = new Window("Math test");
        mainWindow.setContent(doLayout());
        setMainWindow(mainWindow);
    }

    private HorizontalLayout doLayout() {

        HorizontalLayout theLayout = new HorizontalLayout();
        theLayout.setMargin(true);
        theLayout.setSpacing(true);
        theLayout.setSizeFull();

        theLayout.addComponent(doLeftLayout());
        theLayout.addComponent(doRightLayout());

        return theLayout;

    }

    private VerticalLayout doLeftLayout() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);

        mathArea = new RichMathArea();
        mathArea.setCaption("MathArea1");
        content.addComponent(mathArea);

        Button copyButton = new Button("Copy from MathArea1 to MathArea2");
        copyButton.addListener(new Button.ClickListener() {

            private static final long serialVersionUID = 5080315072966878777L;

            public void buttonClick(ClickEvent event) {

                mathArea2.setValue(mathArea.getValue());
            }
        });

        content.addComponent(copyButton);

        mathArea2 = new RichMathArea();
        mathArea2.setCaption("MathArea2");
        content.addComponent(mathArea2);

        return content;
    }

    private VerticalLayout doRightLayout() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);

        mathArea3 = new RichMathArea();
        mathArea3.setCaption("MathArea3");
        content.addComponent(mathArea3);

        Button renderButton = new Button("Render MathArea3 as Labels");

        renderButton.addListener(new Button.ClickListener() {

            private static final long serialVersionUID = 2170006282074068877L;

            public void buttonClick(ClickEvent event) {
                componentLayout.removeAllComponents();
                List<Component> components = MathValueParser.get()
                        .getMathValueAsComponents(mathArea3);
                for (Component c : components) {
                    componentLayout.addComponent(c);
                }

            }
        });

        content.addComponent(renderButton);

        componentLayout = new CssLayout();
        componentLayout.setWidth("400px");
        componentLayout.setHeight("300px");
        componentLayout.setCaption("Rendered labels");

        content.addComponent(componentLayout);

        return content;
    }

}
