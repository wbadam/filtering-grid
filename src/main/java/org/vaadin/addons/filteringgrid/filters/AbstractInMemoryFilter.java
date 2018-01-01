package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;

public abstract class AbstractInMemoryFilter<T, V, F> extends
        AbstractFilter<F> implements InMemoryFilter<T, V, F> {

    private static int idCounter = 0;
    private ValueProvider<T, V> valueProvider;
    private SerializableBiPredicate<V, F> filterPredicate;

    public AbstractInMemoryFilter(ValueProvider<T, V> valueProvider,
            HasValue<F> filterComponent,
            SerializableBiPredicate<V, F> filterPredicate) {
        super(getNextId(), filterComponent);
        assert Objects.nonNull(valueProvider) : "Value provider cannot be null";
        assert Objects
                .nonNull(filterPredicate) : "Filter predicate cannot be null";
        this.valueProvider = valueProvider;
        this.filterPredicate = filterPredicate;
    }

    private static String getNextId() {
        return "filter" + idCounter++;
    }

    @Override
    public ValueProvider<T, V> getValueProvider() {
        return valueProvider;
    }

    @Override
    public SerializableBiPredicate<V, F> getFilterPredicate() {
        return filterPredicate;
    }
}
