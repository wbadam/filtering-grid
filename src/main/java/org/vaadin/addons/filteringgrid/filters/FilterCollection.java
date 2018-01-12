package org.vaadin.addons.filteringgrid.filters;

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

    public Optional getValue(String key) {
        return filters.stream().filter(f -> f.getKey().equals(key)).findAny()
                .map(Filter::getValue);
    }

    public <F> Optional<F> getValue(String key, Class<F> fClass) {
        return filters.stream().filter(f -> f.getKey().equals(key))
                .filter(f -> f.getType().equals(fClass)).map(f -> (Filter<F>) f)
                .findAny().map(Filter::getValue);
    }
}
