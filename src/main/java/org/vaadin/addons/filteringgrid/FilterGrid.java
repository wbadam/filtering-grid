package org.vaadin.addons.filteringgrid;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.addons.filteringgrid.filters.Filter;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.data.provider.InMemoryDataProviderHelpers;
import com.vaadin.server.SerializableFunction;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Grid;

public class FilterGrid<T> extends Grid<T> {

    private Set<Filter<?>> filters = new HashSet<>();

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

    @Override
    protected void internalSetDataProvider(DataProvider<T, ?> dataProvider) {
        if (dataProvider instanceof InMemoryDataProvider) {

//            InMemoryDataProviderHelpers.filteringByIgnoreNull(dataProvider, )
            super.internalSetDataProvider(((InMemoryDataProvider) dataProvider).withConvertedFilter((SerializableFunction<Collection<InMemoryFilter>, SerializablePredicate<T>>) filters -> item -> filters.stream().anyMatch(filter -> filter.test(item))), filters);
        } else {
            super.internalSetDataProvider(dataProvider);
        }
//        super.internalSetDataProvider(dataProvider.withConvertedFilter(), );
//        for (Column<T, ?> column : getColumns()) {
//            column.updateSortable();
//        }
    }

    public <F> void addFilter(Filter<F> filter) {
        filters.add(filter);
        filter.addValueChangeListener(event -> getDataProvider().refreshAll());
    }
}
