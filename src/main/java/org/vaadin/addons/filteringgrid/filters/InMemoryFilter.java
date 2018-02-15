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
     * Helper class for comparing {@link Comparable} objects.
     */
    public static class Comparator {

        /**
         * Compares two objects and decides whether one is smaller than the
         * other. Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable objects
         * @return a predicate that does the comparison
         */
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> smallerThan() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) < 0);
        }

        /**
         * Compares two objects and decides whether one is smaller than or equal
         * to the other. Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable objects
         * @return a predicate that does the comparison
         */
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> smallerThanOrEquals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) <= 0);
        }

        /**
         * Compares two objects and decides whether one equal to the other.
         * Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable objects
         * @return a predicate that does the comparison
         */
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> equals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) == 0);
        }

        /**
         * Compares two objects and decides whether one is greater than or equal
         * to the other. Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable objects
         * @return a predicate that does the comparison
         */
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> greaterThanOrEquals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) >= 0);
        }

        /**
         * Compares two objects and decides whether one is greater than the
         * other. Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable objects
         * @return a predicate that does the comparison
         */
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> greaterThan() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) > 0);
        }

        private static <T, U> SerializableBiPredicate<T, U> ignoreNull(
                SerializableBiPredicate<T, U> predicate) {
            return (value, filterValue) -> value == null || filterValue == null
                    || predicate.test(value, filterValue);
        }
    }

    /**
     * Helper class for comparing {@link String} objects.
     */
    public static class StringComparator {

        /**
         * Compares an object's {@code toString()} value to another string and
         * decides whether the first contains the second. Ignores {@code null}
         * values.
         *
         * @param <T>
         *         type of the comparable object
         * @return a predicate that does the comparison
         */
        public static <T> SerializableBiPredicate<T, String> contains() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString()
                            .contains(filterValue));
        }

        /**
         * Compares an object's {@code toString()} value to another string and
         * decides whether the first contains the second, ignoring case. Ignores
         * {@code null} values.
         *
         * @param <T>
         *         type of the comparable object
         * @return a predicate that does the comparison
         */
        public static <T> SerializableBiPredicate<T, String> containsIgnoreCase() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString().toLowerCase()
                            .contains(filterValue.toLowerCase()));
        }

        /**
         * Compares an object's {@code toString()} value to another string and
         * decides whether the first starts with the second. Ignores {@code
         * null} values.
         *
         * @param <T>
         *         type of the comparable object
         * @return a predicate that does the comparison
         */
        public static <T> SerializableBiPredicate<T, String> startsWith() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString()
                            .startsWith(filterValue));
        }

        /**
         * Compares an object's {@code toString()} value to another string and
         * decides whether the first starts with the second, ignoring case.
         * Ignores {@code null} values.
         *
         * @param <T>
         *         type of the comparable object
         * @return a predicate that does the comparison
         */
        public static <T> SerializableBiPredicate<T, String> startsWithIgnoreCase() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString().toLowerCase()
                            .startsWith(filterValue.toLowerCase()));
        }
    }

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
