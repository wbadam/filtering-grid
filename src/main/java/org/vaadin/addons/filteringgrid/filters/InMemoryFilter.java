package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Component;

/**
 * Filter that can be attached to {@link org.vaadin.addons.filteringgrid.FilterGrid
 * FilterGrid} and be used to filter it's in-memory items.
 *
 * @param <T>
 *         the grid bean type
 * @param <V>
 *         filterable value type
 * @param <F>
 *         value type of the filter
 */
public interface InMemoryFilter<T, V, F> extends Filter<F>,
        SerializablePredicate<T> {

    /**
     * Gets the value provider that converts the grid's bean to a filterable
     * value.
     *
     * @return the value provider
     */
    public ValueProvider<T, V> getValueProvider();

    /**
     * Gets the predicate for deciding whether a value should pass the filter.
     *
     * @return the filter predicate
     */
    public SerializableBiPredicate<V, F> getFilterPredicate();

    /**
     * Evaluates the filter predicate on the provided value and the filter
     * value.
     *
     * @param t
     *         the bean item on which to run the evaluation
     * @return {@code true} if the item matches passes the filter, otherwise
     * {@code false}
     */
    @Override
    public default boolean test(T t) {
        return getFilterPredicate()
                .test(getValueProvider().apply(t), getValue());
    }

    /**
     * Helper method for creating an in-memory filter out of a component.
     *
     * @param component
     *         the filter component
     * @param valueProvider
     *         value provider that converts a bean item to a filterable value
     * @param filterPredicate
     *         predicate that evaluates whether a value passes the filter
     * @param <T>
     *         grid bean type
     * @param <V>
     *         filterable value type
     * @param <F>
     *         filter value type
     * @param <C>
     *         filter component type
     * @return an in-memory filter that uses the given component as filtering
     * component
     */
    public static <T, V, F, C extends Component & HasValue<F>> InMemoryFilterComponentWrapper<T, V, F, C> wrapComponent(
            C component, ValueProvider<T, V> valueProvider,
            SerializableBiPredicate<V, F> filterPredicate) {
        return new InMemoryFilterComponentWrapper<T, V, F, C>(
                KeyGenerator.generateKey(), component) {
            @Override
            public ValueProvider<T, V> getValueProvider() {
                return valueProvider;
            }

            @Override
            public SerializableBiPredicate<V, F> getFilterPredicate() {
                return filterPredicate;
            }
        };
    }
}
