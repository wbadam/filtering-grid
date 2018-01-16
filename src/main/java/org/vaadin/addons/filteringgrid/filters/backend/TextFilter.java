package org.vaadin.addons.filteringgrid.filters.backend;

import org.vaadin.addons.filteringgrid.filters.AbstractFilter;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.TextField;

public class TextFilter extends AbstractFilter<String> {

    private TextField comp;

    public TextFilter(String filterKey, TextField filterComponent) {
        super(filterKey, filterComponent, String.class);
        comp = filterComponent;
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<String> listener) {
        return comp.addValueChangeListener(listener);
    }
}
