package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Component;

public interface InMemoryFilter<T, V, F> extends Filter<F>,
        SerializablePredicate<T> {

    public static class Comparator {
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

        private static <T, U> SerializableBiPredicate<T, U> ignoreNull(
                SerializableBiPredicate<T, U> predicate) {
            return (value, filterValue) -> value == null || filterValue == null
                    || predicate.test(value, filterValue);
        }
    }

    public static class StringComparator {
        public static <T> SerializableBiPredicate<T, String> contains() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString()
                            .contains(filterValue));
        }

        public static <T> SerializableBiPredicate<T, String> containsIgnoreCase() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString().toLowerCase()
                            .contains(filterValue.toLowerCase()));
        }

        public static <T> SerializableBiPredicate<T, String> startsWith() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString()
                            .startsWith(filterValue));
        }

        public static <T> SerializableBiPredicate<T, String> startsWithIgnoreCase() {
            return Comparator.ignoreNull(
                    (value, filterValue) -> value.toString().toLowerCase()
                            .startsWith(filterValue.toLowerCase()));
        }
    }

    public ValueProvider<T, V> getValueProvider();

    public SerializableBiPredicate<V, F> getFilterPredicate();

    @Override
    public default boolean test(T t) {
        return getFilterPredicate()
                .test(getValueProvider().apply(t), getValue());
    }

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
