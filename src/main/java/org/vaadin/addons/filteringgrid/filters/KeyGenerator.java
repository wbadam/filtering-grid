package org.vaadin.addons.filteringgrid.filters;

/**
 * Helper class to generate unique identifiers for grid filters.
 */
public class KeyGenerator {

    private static int counter = 0;

    /**
     * Generates unique identifier key.
     *
     * @return a string with {@code "filter"} prefix and a unique number
     */
    public static String generateKey() {
        return "filter" + counter++;
    }
}
