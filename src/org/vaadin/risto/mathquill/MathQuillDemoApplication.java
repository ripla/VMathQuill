package org.vaadin.risto.mathquill;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class MathQuillDemoApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("MathQuill Vaadin integration demo");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
