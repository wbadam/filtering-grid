package org.vaadin.addons.filteringgrid;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.TextFilter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        TextFilter<Person> filter = new TextFilter<>(Person::getFirstName);

        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
        grid.addInMemoryFilter(filter);

        grid.setFilteredDataProvider(DataProvider
                .ofCollection(PersonService.getInstance().getAll()));

        return new VerticalLayout(filter, grid);
    }
}
