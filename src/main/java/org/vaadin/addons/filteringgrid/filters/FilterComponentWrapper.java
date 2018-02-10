package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;

public class FilterComponentWrapper<F, C extends Component & HasValue<F>> extends
        Composite implements FilterComponent<F> {

    private final String key;

    public FilterComponentWrapper(C component) {
        this(KeyGenerator.generateKey(), component);
    }

    protected FilterComponentWrapper(String key, C component) {
        super();

        this.key = key;
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

    @SuppressWarnings("unchecked")
    public C getWrappedComponent() {
        return (C) getCompositionRoot();
    }
}
