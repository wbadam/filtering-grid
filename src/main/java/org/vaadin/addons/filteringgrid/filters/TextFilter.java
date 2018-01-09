package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.shared.Registration;
import com.vaadin.ui.TextField;

public class TextFilter<T> extends AbstractInMemoryFilter<T, String, String> {

    private TextField comp;

    public static SerializableBiPredicate<String, String> CONTAINS = (value, filterValue) ->
            value == null || filterValue == null || value.contains(filterValue);

    public static SerializableBiPredicate<String, String> CONTAINS_IGNORE_CASE = (value, filterValue) ->
            value == null || filterValue == null || value.toLowerCase()
                    .contains(filterValue.toLowerCase());

    public static SerializableBiPredicate<String, String> STARTS_WITH = (value, filterValue) ->
            value == null || filterValue == null || value
                    .startsWith(filterValue);

    public static SerializableBiPredicate<String, String> STARTS_WITH_IGNORE_CASE = (value, filterValue) ->
            value == null || filterValue == null || value.toLowerCase()
                    .startsWith(filterValue.toLowerCase());

    public static SerializableBiPredicate<String, String> EQUALS = Objects::equals;

    public TextFilter(ValueProvider<T, String> valueProvider,
            TextField filterComponent) {
        this(valueProvider, filterComponent, CONTAINS_IGNORE_CASE);
    }

    public TextFilter(ValueProvider<T, String> valueProvider,
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
