package org.vaadin.addons.filteringgrid.filters;


import java.util.function.Function;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.ui.TextField;

public abstract class NumberFilter<T, N extends Number & Comparable<N>> extends
        InMemoryFilterComponentWrapper<T, N, String, TextField> {

    @FunctionalInterface
    public interface StringToNumberConverter<N extends Number & Comparable> extends
            Function<String, N> {
        @Override
        N apply(String s) throws NumberFormatException;
    }

    private NumberFilter(String key, TextField component) {
        super(key, component);
    }

    private static <T, N extends Number & Comparable<N>> NumberFilter<T, N> createNumberFilter(
            ValueProvider<T, N> valueProvider,
            SerializableBiPredicate<N, N> filterPredicate,
            StringToNumberConverter<N> stringToNumberConverter) {
        return new NumberFilter<T, N>(KeyGenerator.generateKey(),
                new TextField()) {
            @Override
            public ValueProvider<T, N> getValueProvider() {
                return valueProvider;
            }

            @Override
            public SerializableBiPredicate<N, String> getFilterPredicate() {
                return (value, filterValue) -> {
                    N filterValueNumber = null;
                    try {
                        filterValueNumber = stringToNumberConverter
                                .apply(filterValue);
                    } catch (NumberFormatException e) {
                        // Filter value is not a number
                    }
                    return filterValueNumber == null || filterPredicate
                            .test(value, filterValueNumber);
                };
            }
        };
    }

    public static <T> NumberFilter<T, Integer> createIntegerFilter(
            ValueProvider<T, Integer> valueProvider,
            SerializableBiPredicate<Integer, Integer> filterPredicate) {
        return createNumberFilter(valueProvider, filterPredicate,
                Integer::valueOf);
    }

    public static <T> NumberFilter<T, Long> createLongFilter(
            ValueProvider<T, Long> valueProvider,
            SerializableBiPredicate<Long, Long> filterPredicate) {
        return createNumberFilter(valueProvider, filterPredicate,
                Long::valueOf);
    }

    public static <T> NumberFilter<T, Float> createFloatFilter(
            ValueProvider<T, Float> valueProvider,
            SerializableBiPredicate<Float, Float> filterPredicate) {
        return createNumberFilter(valueProvider, filterPredicate,
                Float::valueOf);
    }

    public static <T> NumberFilter<T, Double> createDoubleFilter(
            ValueProvider<T, Double> valueProvider,
            SerializableBiPredicate<Double, Double> filterPredicate) {
        return createNumberFilter(valueProvider, filterPredicate,
                Double::valueOf);
    }
}
