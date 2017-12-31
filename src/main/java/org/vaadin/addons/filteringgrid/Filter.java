package org.vaadin.addons.filteringgrid;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializablePredicate;

public class Filter<T, V, F> implements SerializablePredicate<T> {

    private HasValue<F> filterField;
    private ValueProvider<T, V> valueProvider;
    private SerializableBiPredicate<V, F> filterPredicate;

    public Filter(HasValue<F> filterField,
            ValueProvider<T, V> valueProvider,
            SerializableBiPredicate<V, F> filterPredicate) {
        this.filterField = filterField;
        this.valueProvider = valueProvider;
        this.filterPredicate = filterPredicate;
    }

    @Override
    public boolean test(T t) {
        return filterPredicate
                .test(valueProvider.apply(t), filterField.getValue());
    }
}
