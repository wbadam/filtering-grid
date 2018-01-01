package org.vaadin.addons.filteringgrid.filters;

import java.util.Objects;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializablePredicate;

public interface InMemoryFilter<T, V, F> extends Filter<F>,
        SerializablePredicate<T> {

    ValueProvider<T, V> getValueProvider();

    default SerializableBiPredicate<V, F> getFilterPredicate() {
        return Objects::equals;
    }

    @Override
    default boolean test(T t) {
        return getFilterPredicate()
                .test(getValueProvider().apply(t), getValue());
    }
}
