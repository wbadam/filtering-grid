package org.vaadin.addons.filteringgrid.filters;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * An object that holds a collection of filters that are attached to {@link
 * org.vaadin.addons.filteringgrid.FilterGrid FilterGrid}.
 */
public class FilterCollection {

    private static final FilterCollection EMPTY = new FilterCollection(
            Collections.emptyList());

    private final Collection<Filter<?>> filters;

    private FilterCollection(Collection<Filter<?>> filters) {
        this.filters = filters;
    }

    /**
     * Creates a {@link FilterCollection} object from a collection of filters.
     *
     * @param filters
     *         collection of filters from which to create the filter collection
     *         object
     * @return a new filter collection object
     */
    public static FilterCollection createFrom(Collection<Filter<?>> filters) {
        FilterCollection collection = new FilterCollection(filters);
        return collection;
    }

    /**
     * Gets an empty filter collection object.
     *
     * @return and empty filter collection
     */
    public static FilterCollection getEmpty() {
        return EMPTY;
    }

    private Optional<?> getValue(String key) {
        return filters.stream().filter(f -> f.getKey().equals(key)).findAny()
                .map(Filter::getValue);
    }

    /**
     * Gets the value of the filter identified by the given key.
     *
     * @param key
     *         identification key of a filter
     * @param valueType
     *         type of the filter value
     * @param <F>
     *         type of the filter value
     * @return optional value of the filter if the filter with given key exists
     * and is of the given type, otherwise empty Optional
     */
    public <F> Optional<F> getValue(String key, Class<F> valueType) {
        return getValue(key).filter(valueType::isInstance).map(valueType::cast);
    }

    /**
     * Gets the String value of the filter identified by the given key.
     *
     * @param key
     *         identification key of a filter
     * @return optional value of the filter if the filter with given key exists
     * and is of type String, otherwise empty Optional
     */
    public Optional<String> getStringValue(String key) {
        return getValue(key, String.class);
    }

    /**
     * Gets the Integer value of the filter identified by the given key.
     *
     * @param key
     *         identification key of a filter
     * @return optional value of the filter if the filter with given key exists
     * and is of type Integer, otherwise empty Optional
     */
    public Optional<Integer> getIntegerValue(String key) {
        return getValue(key, Integer.class);
    }

    /**
     * Gets the Double value of the filter identified by the given key.
     *
     * @param key
     *         identification key of a filter
     * @return optional value of the filter if the filter with given key exists
     * and is of type Double, otherwise empty Optional
     */
    public Optional<Double> getDoubleValue(String key) {
        return getValue(key, Double.class);
    }

    /**
     * Gets the LocalDate value of the filter identified by the given key.
     *
     * @param key
     *         identification key of a filter
     * @return optional value of the filter if the filter with given key exists
     * and is of type LocalDate, otherwise empty Optional
     */
    public Optional<LocalDate> getLocalDateValue(String key) {
        return getValue(key, LocalDate.class);
    }
}
