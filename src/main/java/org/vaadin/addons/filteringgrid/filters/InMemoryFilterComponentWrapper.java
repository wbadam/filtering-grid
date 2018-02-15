package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

/**
 * Wrapper object for helping create an in-memory filter component.
 *
 * @param <T>
 *         bean item type
 * @param <V>
 *         filterable value type
 * @param <F>
 *         filter value type
 * @param <C>
 *         filter component type
 */
public abstract class InMemoryFilterComponentWrapper<T, V, F, C extends Component & HasValue<F>> extends
        FilterComponentWrapper<F, C> implements InMemoryFilter<T, V, F> {

    /**
     * Creates a new filter component wrapper instance.
     *
     * @param component
     *         component to use as filter
     */
    public InMemoryFilterComponentWrapper(C component) {
        super(component);
    }

    /**
     * Creates a new filter component wrapper instance.
     *
     * @param key
     *         unique key that identifies the filter
     * @param component
     *         component to use as filter
     */
    public InMemoryFilterComponentWrapper(String key, C component) {
        super(key, component);
    }
}
