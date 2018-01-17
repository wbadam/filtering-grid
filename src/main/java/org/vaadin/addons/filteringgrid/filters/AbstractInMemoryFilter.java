package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;

public abstract class AbstractInMemoryFilter<T, V, F> extends
        AbstractFilter<F> implements InMemoryFilter<T, V, F> {

    private ValueProvider<T, V> valueProvider;
    private SerializableBiPredicate<V, F> filterPredicate;

    public AbstractInMemoryFilter(ValueProvider<T, V> valueProvider,
            HasValue<F> filterComponent,
            SerializableBiPredicate<V, F> filterPredicate, Class<F> fClass) {
        super(KeyGenerator.generateKey(), filterComponent, fClass);
        Objects.requireNonNull(valueProvider, "Value provider cannot be null");
        Objects.requireNonNull(filterPredicate,
                "Filter predicate cannot be null");
        this.valueProvider = valueProvider;
        this.filterPredicate = filterPredicate;
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
