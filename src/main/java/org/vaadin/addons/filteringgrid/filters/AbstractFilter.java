package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;

public abstract class AbstractFilter<F> implements Filter<F> {

    private String filterId;
    private HasValue<F> filterComponent;

    public AbstractFilter(String filterId, HasValue<F> filterComponent) {
        this.filterId = filterId;
        this.filterComponent = filterComponent;
    }

    @Override
    public String getId() {
        return filterId;
    }

    @Override
    public F getValue() {
        return filterComponent.getValue();
    }
}
