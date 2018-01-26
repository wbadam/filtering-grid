package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Component;

public interface InMemoryFilter<T, V, F> extends Filter<F>,
        SerializablePredicate<T> {

    class Comparator {
        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> smallerThan() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) < 0);
        }

        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> smallerThanOrEquals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) <= 0);
        }

        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> equals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) == 0);
        }

        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> greaterThanOrEquals() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) >= 0);
        }

        public static <T extends Comparable<T>> SerializableBiPredicate<T, T> greaterThan() {
            return ignoreNull(
                    (value, filterValue) -> value.compareTo(filterValue) > 0);
        }

        private static <T extends Comparable<T>> SerializableBiPredicate<T, T> ignoreNull(
                SerializableBiPredicate<T, T> predicate) {
            return (value, filterValue) -> value == null || filterValue == null
                    || predicate.test(value, filterValue);
        }
    }

    class FilterPredicate {
        public static <V, F> SerializableBiPredicate<V, F> ignoreNullFilter(
                SerializableBiPredicate<V, F> predicate) {
            return (value, filterValue) -> filterValue == null || predicate
                    .test(value, filterValue);
        }
    }

    ValueProvider<T, V> getValueProvider();

    SerializableBiPredicate<V, F> getFilterPredicate();

    @Override
    default boolean test(T t) {
        return getFilterPredicate()
                .test(getValueProvider().apply(t), getValue());
    }

    static <T, V, F, C extends Component & HasValue<F>> FilterComponent<F> wrapComponent(
            C component, Class<F> type, ValueProvider<T, V> valueProvider,
            SerializableBiPredicate<V, F> filterPredicate) {
        return new InMemoryFilterComponentWrapper<T, V, F, C>(
                KeyGenerator.generateKey(), component, type) {
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
