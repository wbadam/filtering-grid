package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;

/**
 * Wrapper object for helping create a filter component.
 *
 * @param <F>
 *         filter value type
 * @param <C>
 *         filter component type
 */
public class FilterComponentWrapper<F, C extends Component & HasValue<F>> extends
        Composite implements Filter<F> {

    private final String key;

    /**
     * Creates a new filter component wrapper instance.
     *
     * @param component
     *         component to wrap and use as filter
     */
    public FilterComponentWrapper(C component) {
        this(KeyGenerator.generateKey(), component);
    }

    /**
     * Creates a new filter component wrapper instance.
     *
     * @param key
     *         unique key that identifies the filter
     * @param component
     *         component to wrap and use as filter
     */
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
