package org.vaadin.addons.filteringgrid.comparators;

import com.vaadin.server.SerializableBiPredicate;

/**
 * Helper class for comparing {@link String} objects.
 */
public class StringComparator {

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
