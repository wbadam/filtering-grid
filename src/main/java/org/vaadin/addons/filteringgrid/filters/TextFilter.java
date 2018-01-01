package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.shared.Registration;
import com.vaadin.ui.TextField;

public class TextFilter<T> extends AbstractInMemoryFilter<T, String, String> {

    private TextField comp;

    public static SerializableBiPredicate<String, String> CONTAINS = (value, filterValue) -> {
        if (value != null && filterValue != null) {
            return value.contains(filterValue);
        } else {
            return true;
        }
    };

    public TextFilter(ValueProvider<T, String> valueProvider, TextField filterComponent) {
        this(valueProvider, filterComponent, CONTAINS);
    }

    public TextFilter(
            ValueProvider<T, String> valueProvider,
            TextField filterComponent,
            SerializableBiPredicate<String, String> filterPredicate) {
        super(valueProvider, filterComponent, filterPredicate);
        this.comp = filterComponent;
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<String> listener) {
        return comp.addValueChangeListener(listener);
    }
}
