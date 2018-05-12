package org.vaadin.addons.filteringgrid;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.StringFilter;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilterGridBackendTest extends AbstractTest {

    private static final String FILTER_FULL_NAME = "fullNameFilter";

    @Override
    public Component getTestComponent() {

        TextField fullNameFilter = new TextField("Filter full name");
        StringFilter<TextField> filterFull = new StringFilter<>
                (FILTER_FULL_NAME, fullNameFilter);

        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
        grid.setColumnOrder("index", "firstName", "lastName", "address",
                "latitude", "longitude", "email", "phone");

        grid.addFilter(filterFull);

        grid.setFilteredDataProvider(
                (sortOrder, filters, offset, limit) -> PersonService
                        .getInstance().getPersonsFilteredName(offset,
                                limit, filters.getValue(FILTER_FULL_NAME,
                                        String.class).orElse(null)),
                filters -> PersonService.getInstance().getSizeFilteredName(
                        filters.getValue(FILTER_FULL_NAME, String.class)
                                .orElse(null)));

        return new VerticalLayout(filterFull, grid);
    }
}
