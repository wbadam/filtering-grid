package org.vaadin.addons.filteringgrid;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.TextFilter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        TextField filter = new TextField();

        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
        grid.addInMemoryFilter(new TextFilter<>(Person::getFirstName, filter));

        grid.setFilteredDataProvider(DataProvider
                .ofCollection(PersonService.getInstance().getAll()));

        return new VerticalLayout(filter, grid);
    }
}
