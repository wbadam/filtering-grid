package org.vaadin.addons.filteringgrid;

import java.util.Arrays;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.comparators.StringComparator;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.Person.Continent;
import org.vaadin.addons.filteringgrid.data.PersonService;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        FilterGrid<Person> grid = new FilterGrid<>(Person.class);

        grid.getColumn("firstName").setFilter(new TextField(),
                StringComparator.containsIgnoreCase());

        grid.getColumn("balance")
                .setFilter(cValue -> (Float) cValue, new TextField(),
                        (v, fv) -> fv.isEmpty() || v < Float.valueOf(fv));

        grid.getColumn("continent").setFilter(
                new ComboBox<>("", Arrays.asList(Continent.values())),
                (cValue, fValue) -> fValue == null || fValue.equals(cValue));

        grid.setFilteredDataProvider(DataProvider
                .ofCollection(PersonService.getInstance().getAll()));

        return new VerticalLayout(grid);
    }
}
