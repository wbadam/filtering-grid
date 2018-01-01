package org.vaadin.addons.filteringgrid;

import java.util.Collection;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.TextFilter;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
//        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
//        grid.getColumn("firstName").setFilterable(true);
//        grid.setItems(PersonService.getInstance().getAll());
//
//
//        DataProvider<Person, Collection<Filter<Person, ?, ?>>> prov;
//        prov = new CallbackDataProvider<>(query -> {
////            query.getf
//            return null;
//        }, query -> {
//            return 0;
//        });
//
//        grid.setDataProvider(prov);
//
////        prov = new InMemoryDataProvider<Person>() {
////
////        }

        TextField filter = new TextField();

        FilterGrid<Person> grid = new FilterGrid<>(Person.class);
        grid.addFilter(new TextFilter<>(Person::getFirstName, filter));
        grid.setItems(PersonService.getInstance().getAll());

        return new VerticalLayout(filter, grid);
    }
}
