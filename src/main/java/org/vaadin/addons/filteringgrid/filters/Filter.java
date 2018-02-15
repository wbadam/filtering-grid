package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;

/**
 * Filter that can be attached to {@link org.vaadin.addons.filteringgrid.FilterGrid
 * FilterGrid} and be used to filter it's items.
 *
 * @param <F>
 *         the value type of the filter
 */
public interface Filter<F> {

    /**
     * Gets a unique key with which to identify this filter.
     *
     * @return a unique identifier
     */
    public String getKey();

    /**
     * Gets the value of the filter.
     *
     * @return value of the filter
     */
    public F getValue();

    /**
     * Adds a value change listener to this filter.
     *
     * @param listener
     *         a listener that will be triggered when the filter value changes
     * @return a registration object for the listener
     */
    public Registration addValueChangeListener(ValueChangeListener<F> listener);
}
