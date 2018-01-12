package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.HasValue;

public abstract class AbstractFilter<F> implements Filter<F> {

    private String filterKey;
    private HasValue<F> filterComponent;
    private Class<F> type;

    public AbstractFilter(String filterKey, HasValue<F> filterComponent,
            Class<F> fClass) {
        Objects.requireNonNull(filterKey, "Filter key must not be null");
        Objects.requireNonNull(filterComponent,
                "Filter component must not be null");
        this.filterKey = filterKey;
        this.filterComponent = filterComponent;
        type = fClass;
    }

    @Override
    public String getKey() {
        return filterKey;
    }

    @Override
    public F getValue() {
        return filterComponent.getValue();
    }

    @Override
    public Class<F> getType() {
        return type;
    }
}
