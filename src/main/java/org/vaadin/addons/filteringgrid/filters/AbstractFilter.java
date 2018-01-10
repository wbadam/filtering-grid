package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.HasValue;

public abstract class AbstractFilter<F> implements Filter<F> {

    private String filterKey;
    private HasValue<F> filterComponent;

    public AbstractFilter(String filterKey, HasValue<F> filterComponent) {
        Objects.requireNonNull(filterKey, "Filter key must not be null");
        Objects.requireNonNull(filterComponent,
                "Filter component must not be null");
        this.filterKey = filterKey;
        this.filterComponent = filterComponent;
    }

    @Override
    public String getKey() {
        return filterKey;
    }

    @Override
    public F getValue() {
        return filterComponent.getValue();
    }
}
