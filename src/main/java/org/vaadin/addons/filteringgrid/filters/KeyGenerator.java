package org.vaadin.addons.filteringgrid.filters;

public class KeyGenerator {

    private static int counter = 0;

    public static String generateKey() {
        return "filter" + counter++;
    }
}
