package org.vaadin.addons.filteringgrid.comparators;

import com.vaadin.server.SerializableBiPredicate;

/**
 * Helper class for comparing {@link Comparable} objects.
 */
public class Comparator {

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

    static <T, U> SerializableBiPredicate<T, U> ignoreNull(
            SerializableBiPredicate<T, U> predicate) {
        return (value, filterValue) -> value == null || filterValue == null
                || predicate.test(value, filterValue);
    }
}
