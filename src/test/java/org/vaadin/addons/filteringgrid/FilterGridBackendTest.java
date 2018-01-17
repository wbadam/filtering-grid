package org.vaadin.addons.filteringgrid;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.StringFilter;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilterGridBackendTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        TextField field = new TextField();

        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
        grid.addFilter(new StringFilter("firstName", field));

        grid.setFilteredDataProvider(
                (sortOrder, filters, offset, limit) -> PersonService
                        .getInstance().getPersons(offset, limit,
                                filters.getValue("firstName", String.class)
                                        .orElse(null)),
                filters -> PersonService.getInstance().getSize(
                        filters.getValue("firstName", String.class)
                                .orElse(null)));

        return new VerticalLayout(field, grid);
    }
}
