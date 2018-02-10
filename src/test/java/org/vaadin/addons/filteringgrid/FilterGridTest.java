package org.vaadin.addons.filteringgrid;

import java.util.Arrays;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.Person.Continent;
import org.vaadin.addons.filteringgrid.data.PersonService;
import org.vaadin.addons.filteringgrid.filters.ComboBoxFilter;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter.Comparator;
import org.vaadin.addons.filteringgrid.filters.NumberFilter;
import org.vaadin.addons.filteringgrid.filters.TextFilter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        FilterGrid<Person> grid = new FilterGrid<>(Person.class);

        grid.getColumn("firstName")
                .setFilter(new TextFilter<>(Person::getFirstName));

//        grid.getColumn("balance").setFilter(InMemoryFilter
//                .wrapComponent(new TextField(), String.class,
//                        Person::getBalance, (value, filterValue) -> {
//                            Float filterValueFloat = null;
//                            try {
//                                filterValueFloat = Float.valueOf(filterValue);
//                            } catch (NumberFormatException e) {
//
//                            }
//                            return filterValueFloat != null ?
//                                    value.compareTo(filterValueFloat) < 0
//                                    : true;
//                        }));
        grid.getColumn("balance").setFilter(NumberFilter
                .createFloatFilter(Person::getBalance,
                        Comparator.smallerThan()));

        grid.getColumn("continent").setFilter(
                new ComboBoxFilter<>(Arrays.asList(Continent.values()),
                        Person::getContinent,
                        (v, f) -> f == null || f.equals(v)));

        grid.setFilteredDataProvider(DataProvider
                .ofCollection(PersonService.getInstance().getAll()));

        return new VerticalLayout(grid);
    }
}
