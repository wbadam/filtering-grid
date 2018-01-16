package org.vaadin.addons.filteringgrid;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import org.vaadin.addons.filteringgrid.filters.Filter;
import org.vaadin.addons.filteringgrid.filters.FilterCollection;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.ui.Grid;

public class FilterGrid<T> extends Grid<T> {

    @FunctionalInterface
    public interface FetchFilteredItemsCallback<T> extends Serializable {

        /**
         * Returns a stream of items ordered by given sort orders, limiting the
         * results with given offset and limit.
         * <p>
         * This method is called after the size of the data set is asked from a
         * related size callback. The offset and limit are promised to be within
         * the size of the data set.
         *
         * @param sortOrder
         *         a list of sort orders
         * @param offset
         *         the first index to fetch
         * @param limit
         *         the fetched item count
         * @return stream of items
         */
        public Stream<T> fetchItems(List<QuerySortOrder> sortOrder,
                FilterCollection filters, int offset, int limit);
    }

    @FunctionalInterface
    public interface CountFilteredItemsCallback extends Serializable {
        public Integer countItems(FilterCollection filters);
    }

    private Collection<Filter<?>> filters = new HashSet<>();

    public FilterGrid() {
        super();
    }

    public FilterGrid(Class<T> beanType) {
        super(beanType);
    }

    public FilterGrid(String caption) {
        super(caption);
    }

    public FilterGrid(String caption, DataProvider<T, ?> dataProvider) {
        super(caption, dataProvider);
    }

    public FilterGrid(DataProvider<T, ?> dataProvider) {
        super(dataProvider);
    }

    public FilterGrid(String caption, Collection<T> items) {
        super(caption, items);
    }

    public void setFilterableDataProvider(DataProvider<T, Collection<InMemoryFilter<T, ?, ?>>> dataProvider) {
//        super.setDataProvider(dataProvider);
        internalSetDataProvider((InMemoryDataProvider) dataProvider, filters);
    }

    public void setFilteredDataProvider(
            FetchFilteredItemsCallback<T> fetchItems,
            CountFilteredItemsCallback sizeCallback) {
        internalSetDataProvider(new CallbackDataProvider<T, FilterCollection>(
                        q -> fetchItems.fetchItems(q.getSortOrders(),
                                q.getFilter().orElse(FilterCollection.getEmpty()),
                                q.getOffset(), q.getLimit()), q -> sizeCallback
                        .countItems(q.getFilter().orElse(FilterCollection.getEmpty()))),
                FilterCollection.createFrom(filters));
    }

//    @Override
//    protected void internalSetDataProvider(DataProvider<T, ?> dataProvider) {
//        if (dataProvider instanceof InMemoryDataProvider) {
//
////            InMemoryDataProviderHelpers.filteringByIgnoreNull(dataProvider, )
//            super.internalSetDataProvider(((InMemoryDataProvider) dataProvider)
//                            .withConvertedFilter(
//                                    (SerializableFunction<Collection<InMemoryFilter>, SerializablePredicate<T>>) filters -> item -> filters
//                                            .stream()
//                                            .anyMatch(filter -> filter.test(item))),
//                    filters);
//        } else {
//            super.internalSetDataProvider(dataProvider);
//        }
////        super.internalSetDataProvider(dataProvider.withConvertedFilter(), );
////        for (Column<T, ?> column : getColumns()) {
////            column.updateSortable();
////        }
//    }

    public <F> void addFilter(Filter<F> filter) {
        filters.add(filter);
        filter.addValueChangeListener(event -> getDataProvider().refreshAll());
    }

//    @Override
//    protected void writeData(Element body, DesignContext designContext) {
//        new Query<T, Collection<Filter>>();
//        getDataProvider().fetch(new Query<>())
//                .forEach(item -> writeRow(body, item, designContext));
//    }
//
//    /**
//     * @see Grid#writeRow(Element, Object, DesignContext)
//     */
//    private void writeRow(Element container, T item, DesignContext context) {
//        Element tableRow = container.appendElement("tr");
//        tableRow.attr("item", serializeDeclarativeRepresentation(item));
//        if (getSelectionModel().isSelected(item)) {
//            tableRow.attr("selected", "");
//        }
//        for (Column<T, ?> column : getColumns()) {
//            Object value = column.getValueProvider().apply(item);
//            tableRow.appendElement("td")
//                    .append(Optional.ofNullable(value).map(Object::toString)
//                            .map(DesignFormatter::encodeForTextNode)
//                            .orElse(""));
//        }
//    }
}
