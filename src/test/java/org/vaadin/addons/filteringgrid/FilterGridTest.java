package org.vaadin.addons.filteringgrid;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.addons.filteringgrid.comparators.Comparator;
import org.vaadin.addons.filteringgrid.comparators.StringComparator;
import org.vaadin.addons.filteringgrid.components.DateRangeField;
import org.vaadin.addons.filteringgrid.data.Person;
import org.vaadin.addons.filteringgrid.data.Person.Continent;
import org.vaadin.addons.filteringgrid.data.PersonService;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;

public class FilterGridTest extends AbstractTest {

    @Override
    public Component getTestComponent() {
        FilterGrid<Person> grid = new FilterGrid<>();
        grid.setWidth("100%");

        FilterGrid.Column<Person, Integer> colIndex = grid.addColumn(Person::getIndex);
        FilterGrid.Column<Person, String> colFirstName = grid.addColumn(Person::getFirstName);
        FilterGrid.Column<Person, String> colLastName = grid.addColumn(Person::getLastName);
        FilterGrid.Column<Person, Date> colDateOfBirth = grid.addColumn(Person::getDateOfBirth, new DateRenderer("%1$tB %1$te, %1$tY", Locale.ENGLISH));
        FilterGrid.Column<Person, String> colAddress = grid.addColumn(Person::getAddress);
        FilterGrid.Column<Person, Double> colLatitude = grid.addColumn(Person::getLatitude);
        FilterGrid.Column<Person, Double> colLongitude = grid.addColumn(Person::getLongitude);
        FilterGrid.Column<Person, Continent> colContinent = grid.addColumn(Person::getContinent);
        FilterGrid.Column<Person, String> colEmail = grid.addColumn(Person::getEmail);
        FilterGrid.Column<Person, String> colPhone = grid.addColumn(Person::getPhone);
        FilterGrid.Column<Person, String> colCompany = grid.addColumn(Person::getCompany);
        FilterGrid.Column<Person, Float> colBalance = grid.addColumn(Person::getBalance);
        FilterGrid.Column<Person, Date> colRegistered = grid.addColumn(Person::getRegistered);
        FilterGrid.Column<Person, Boolean> colActive = grid.addColumn(Person::isActive);
        FilterGrid.Column<Person, String> colGuid = grid.addColumn(Person::getGuid);

        colFirstName.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colLastName.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colDateOfBirth.setFilter(v -> v.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new DateField(), (v, fv) -> fv == null || v.isEqual(fv));
        colAddress.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colRegistered.setFilter(v -> v.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new DateRangeField(), (v, fv) -> fv == null || (!v.isBefore(fv.getDateFrom()) && !v.isAfter(fv.getDateTo())));
        colEmail.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colPhone.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colCompany.setFilter(new TextField(), StringComparator.containsIgnoreCase());
        colBalance.setFilter(new TextField(), (v, fv) -> fv.isEmpty() || v < Float.valueOf(fv));
        colContinent.setFilter(new ComboBox<>(null, Arrays.asList(Continent.values())), Comparator.equals());
        colActive.setFilter(new ComboBox<>(null, Arrays.asList(true, false)), Comparator.equals());

        grid.setFilteredDataProvider(DataProvider
                .ofCollection(PersonService.getInstance().getAll()));

        return new VerticalLayout(grid);
    }
}
