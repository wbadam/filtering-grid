package org.vaadin.addons.filteringgrid;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        Grid<Person> grid = new Grid<>(Person.class);
        grid.setItems(PersonService.getInstance().getAll());
        return grid;
    }
}
