package org.vaadin.addons.filteringgrid.filters;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class FilterCollection {

    private static final FilterCollection EMPTY = new FilterCollection(
            Collections.emptyList());

    private final Collection<Filter<?>> filters;

    private FilterCollection(Collection<Filter<?>> filters) {
        this.filters = filters;
    }

    public static FilterCollection createFrom(Collection<Filter<?>> filters) {
        FilterCollection collection = new FilterCollection(filters);
        return collection;
    }

    public static FilterCollection getEmpty() {
        return EMPTY;
    }

    private Optional<?> getValue(String key) {
        return filters.stream().filter(f -> f.getKey().equals(key)).findAny()
                .map(Filter::getValue);
    }

    public <F> Optional<F> getValue(String key, Class<F> valueType) {
        return getValue(key).filter(valueType::isInstance).map(valueType::cast);
    }

    public Optional<String> getStringValue(String key) {
        return getValue(key, String.class);
    }

    public Optional<Integer> getIntegerValue(String key) {
        return getValue(key, Integer.class);
    }

    public Optional<Double> getDoubleValue(String key) {
        return getValue(key, Double.class);
    }

    public Optional<LocalDate> getLocalDateValue(String key) {
        return getValue(key, LocalDate.class);
    }
}
