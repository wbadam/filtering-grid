package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.TextField;

public class StringFilter extends AbstractFilter<String> {

    private HasValue<String> filterComponent;

    public StringFilter(String filterKey, TextField filterComponent) {
        super(filterKey, filterComponent, String.class);
        this.filterComponent = filterComponent;
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<String> listener) {
        return filterComponent.addValueChangeListener(listener);
    }
}
