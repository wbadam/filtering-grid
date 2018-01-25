package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;

public class FilterComponentWrapper<F, C extends Component & HasValue<F>> extends
        Composite implements FilterComponent<F> {

    private final String key;
    private final Class<F> type;

    protected FilterComponentWrapper(C component, Class<F> type) {
        this(KeyGenerator.generateKey(), component, type);
    }

    protected FilterComponentWrapper(String key, C component, Class<F> type) {
        super();

        this.key = key;
        this.type = type;
        setCompositionRoot(component);
    }

    @Override
    public F getValue() {
        return getWrappedComponent().getValue();
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<F> listener) {
        return getWrappedComponent().addValueChangeListener(listener);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class<F> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public C getWrappedComponent() {
        return (C) getCompositionRoot();
    }
}
