# Filtering Grid

The Filtering Grid component makes it easy to filter data displayed in a Grid.

## Usage examples

### Filtering in-memory data

```Java
// Create the Grid component
FilterGrid<Person> grid = new FilterGrid<>();

// Add columns to the Grid
FilterGrid.Column<Person, String> colFirstName = grid.addColumn(Person::getFirstName);
FilterGrid.Column<Person, LocalDate> colDateOfBirth = grid.addColumn(Person::getDateOfBirth);
FilterGrid.Column<Person, Continent> colContinent = grid.addColumn(Person::getContinent);
FilterGrid.Column<Person, Float> colBalance = grid.addColumn(Person::getBalance);

// --- Filters ---

// Add a text field to the header to filter the firstName column.
// Data that contains the filter text regardless of case will be displayed.
colFirstName.setFilter(
        new TextField(), StringComparator.containsIgnoreCase());

// Add a filter for dateOfBirth
// The Grid will display rows that have birth date equal to the one set in the filter
colDateOfBirth.setFilter(
        new DateField(), (v, fv) -> fv == null || v.isEqual(fv));

// Add a combo box to filter the continent column
colContinent.setFilter(
        new ComboBox<>("", Arrays.asList(Continent.values())),
        (cValue, fValue) -> fValue == null || fValue.equals(cValue));
```

#### Explanation

The `FilterColumn#setFilter()` method accepts a `Component` that will show up in the Grid's header row for the column, and a `SerializableBiPredicate<COLUMN_VALUE, FILTER_VALUE>` which instructs the Grid to display a given row or not.
The BiPredicate is a function that gets two parameters, the value and the filter's value, and returns `true` (keep the row) or `false` (filter out the row).

In addition, in case the value is of different type than the filter's value, it is possible to provide a value provider which converts the value to a filterable value. For example in case the date of birth is of type `Date` but the filter is `LocalDate` (as `DateField`s are in Vaadin 8), one can use the following converter as firsrt parameter.

```Java
v -> v.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
```

The component will then convert each row's value to one that is filterable by the filter component. In this case the `setFilter()` method looks like the following.

```Java
colDateOfBirth.setFilter(
        v -> v.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
        new DateField(),
        (v, fv) -> fv == null || v.isEqual(fv));
```  

### Filtering backend data

With backend data there is no way of using a common method of filtering since all backend are different.
Nonetheless, the component provides a standardised way of adding filters for any backend.

Given the backend service that returns a filtered stream of persons by first name in this case.

```Java
/* Returns a stream of persons filtered by given conditions, e.g. from database. */
public Stream<Person> getPersonsFilteredByFirstName(int offset, int limit, String firstNameContains) {
    // ...
}
```

Then we can create a data provider that receives the defined filter values and uses the above service for getting data for the Grid.

```Java
FilterGrid<Person> grid = new FilterGrid<>(Person.class);

// Create String filter by giving it a key and a component, then add it to the Grid
StringFilter<TextField> filter = new StringFilter<>("filterFirstName", new TextField());
grid.addFilter(filter);

// Use the special data provider to receive all given filters as FilterCollection
grid.setFilteredDataProvider(
        (sortOrder, filters, offset, limit) -> PersonService.getInstance()
                .getPersons(offset, limit, filters.getStringValue("filterFirstName")
                        .orElse(null)),
        filters -> PersonService.getInstance().getSize(
                filters.getValue("firstName", String.class)
                        .orElse(null)));

```
#### Explanation

Using the `setFilteredDataProvider(FetchFilteredItemsCallback<T>, CountFilteredItemsCallback)` method, which works very similar to the framework's `setDataProvider()` method, we receive a `FilterCollection` object which contains all the attached filters and their current values.
Given the filter values, it is easy to use a previously given service, which could query a data base or any other source, to fetch the required filtered data.

The `FetchFilteredItemsCallback` receives the sort order, offset, limit and, additionally, the filter collection, and returns a stream of data.
Similarly, the `CountFilteredItemsCallback` receives the filters and returns the size of the filtered data set.

#### What can be filters?

One can use any object as filter which implements the `Filter` interface.

Components are most used as filters and and for that there is a helper class, `FilterComponentWrapper`, which helps converting any component into a filter.
Also, filters that have textual value, can be created using `StringFilter`.

## Differences between in-memory and backend filters

In-memory data is easier to filter since the data is given. In case of backend data, there can be many implementations, using a database, REST service etc.

In-memory data can easily utilise a predicate getting two values and returning a boolean, while backend needs to be implemented case by case.

## How to add filters?

Currently it is possible to add any object implementing the `Filter` interface via the `FilterGrid#addFilter()` method.

On top of that, it is possible to set one designated component as filter to each column using one of the `FilterGrid.Column#setFilter()` methods.
In the latter case, the component will be attached to the header section of the Grid above the column.
