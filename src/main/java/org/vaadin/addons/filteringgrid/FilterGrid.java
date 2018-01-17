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
import com.vaadin.server.SerializableFunction;
import com.vaadin.server.SerializablePredicate;
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

    private SerializableFunction<Collection<Filter<?>>, SerializablePredicate<T>> filterConverter = filters -> item -> filters
            .stream().filter(f -> f instanceof InMemoryFilter)
            .map(f -> (InMemoryFilter<T, ?, ?>) f).anyMatch(f -> f.test(item));

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

    public FilterGrid(String caption, Collection<T> items) {
        super(caption);
        setItems(items);
    }

    public void setFilteredDataProvider(InMemoryDataProvider<T> dataProvider) {
        internalSetDataProvider(dataProvider.withConvertedFilter(
                filterConverter), filters);
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

    @Override
    public void setItems(Collection<T> items) {
        internalSetDataProvider(DataProvider.ofCollection(items)
                .withConvertedFilter(filterConverter), filters);
    }

    public <F> void addFilter(Filter<F> filter) {
        filters.add(filter);
        filter.addValueChangeListener(event -> getDataProvider().refreshAll());
    }

    public <V, F> void addInMemoryFilter(InMemoryFilter<T, V, F> filter) {
        this.addFilter(filter);
    }
}
