package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.Registration;

public interface Filter<F> {

    String getKey();

    F getValue();

    Registration addValueChangeListener(ValueChangeListener<F> listener);
}
