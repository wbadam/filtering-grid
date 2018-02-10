package org.vaadin.addons.filteringgrid.filters;

import java.util.Collection;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.ui.ComboBox;

public class ComboBoxFilter<T, V> extends
        InMemoryFilterComponentWrapper<T, V, V, ComboBox<V>> {

    private final ValueProvider<T, V> valueProvider;
    private final SerializableBiPredicate<V, V> filterPredicate;

    public ComboBoxFilter(Collection<V> items,
            ValueProvider<T, V> valueProvider,
            SerializableBiPredicate<V, V> filterPredicate) {
        super(new ComboBox<>());

        this.valueProvider = valueProvider;
        this.filterPredicate = filterPredicate;

        getWrappedComponent().setItems(items);
    }

    @Override
    public ValueProvider<T, V> getValueProvider() {
        return valueProvider;
    }

    @Override
    public SerializableBiPredicate<V, V> getFilterPredicate() {
        return filterPredicate;
    }
}
