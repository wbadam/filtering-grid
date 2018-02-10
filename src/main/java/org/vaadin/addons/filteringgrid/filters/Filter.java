package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;

public interface Filter<F> {

    public String getKey();

    public F getValue();

    public Registration addValueChangeListener(ValueChangeListener<F> listener);
}
