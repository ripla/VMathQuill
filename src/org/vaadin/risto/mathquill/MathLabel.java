package org.vaadin.risto.mathquill;

import java.lang.reflect.Method;

import org.vaadin.risto.mathquill.client.ui.Communication;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * <p>
 * Label that uses the MathQuill javascript library to render math symbols in
 * the browser.
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin Ltd.
 * @see MathTextField
 * @see <a href="https://github.com/laughinghan/mathquill">MathQuill@GitHub</a>
 */
@com.vaadin.ui.ClientWidget(org.vaadin.risto.mathquill.client.ui.VMathLabel.class)
public class MathLabel extends AbstractComponent implements Property,
        Property.Viewer, Property.ValueChangeListener,
        Property.ValueChangeNotifier {

    private static final long serialVersionUID = -4700221613987326009L;
    private Property datasource;

    private static final Method VALUE_CHANGE_METHOD;
    static {
        try {
            VALUE_CHANGE_METHOD = Property.ValueChangeListener.class
                    .getDeclaredMethod("valueChange",
                            new Class[] { Property.ValueChangeEvent.class });
        } catch (final java.lang.NoSuchMethodException e) {
            throw new java.lang.RuntimeException(
                    "Internal error finding methods in MathLabel");
        }
    }

    public MathLabel() {
        this("");
    }

    public MathLabel(String content) {
        this(new ObjectProperty<String>(content, String.class));
    }

    public MathLabel(Property contentSource) {
        setPropertyDataSource(contentSource);
        setWidth(100, UNITS_PERCENTAGE);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (hasValue()) {
            target.addAttribute(Communication.ATT_CONTENT, getValue());
        }
    }

    public boolean hasValue() {
        return getValue() != null && !"".equals(getValue());
    }

    public String getValue() {
        return (String) datasource.getValue();
    }

    public void setValue(Object newValue) throws ReadOnlyException,
            ConversionException {
        datasource.setValue(newValue);

    }

    public Class<?> getType() {
        return String.class;
    }

    public void addListener(ValueChangeListener listener) {
        addListener(MathLabel.ValueChangeEvent.class, listener,
                VALUE_CHANGE_METHOD);
    }

    public void removeListener(ValueChangeListener listener) {
        removeListener(MathLabel.ValueChangeEvent.class, listener,
                VALUE_CHANGE_METHOD);
    }

    public void valueChange(Property.ValueChangeEvent event) {
        fireValueChange();
    }

    protected void fireValueChange() {
        // Set the error message
        fireEvent(new MathLabel.ValueChangeEvent(this));
        requestRepaint();
    }

    public void setPropertyDataSource(Property newDataSource) {
        if (newDataSource == null || newDataSource.getType() != String.class) {
            throw new IllegalArgumentException(
                    "Datasources of MathLabel must be non-null and of type String. Given: "
                            + newDataSource);
        }

        // Stops listening the old data source changes
        if (datasource != null
                && Property.ValueChangeNotifier.class
                        .isAssignableFrom(datasource.getClass())) {
            ((Property.ValueChangeNotifier) datasource).removeListener(this);
        }

        this.datasource = newDataSource;

        // Listens the new data source if possible
        if (Property.ValueChangeNotifier.class.isAssignableFrom(datasource
                .getClass())) {
            ((Property.ValueChangeNotifier) datasource).addListener(this);
        }

        requestRepaint();
    }

    public Property getPropertyDataSource() {
        return datasource;
    }

    /**
     * Value change event for changes in the MathLabel.
     * 
     * @author Risto Yrjänä / Vaadin Ltd.
     * 
     */
    public class ValueChangeEvent extends Component.Event implements
            Property.ValueChangeEvent {

        private static final long serialVersionUID = -4757508599903114291L;

        public ValueChangeEvent(MathLabel source) {
            super(source);
        }

        public Property getProperty() {
            return (Property) getSource();
        }
    }
}
