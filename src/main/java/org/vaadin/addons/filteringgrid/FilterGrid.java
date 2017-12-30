package org.vaadin.addons.filteringgrid;

import java.util.Collection;
import java.util.stream.Stream;

import com.vaadin.data.PropertySet;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.SerializableFunction;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.AbstractRenderer;
import com.vaadin.ui.renderers.Renderer;

public class FilterGrid<T> extends Grid<T> {

    public static class Column<T, V> extends Grid.Column<T, V> {

        private boolean filterable;
        private SerializableFunction<T, ? extends V> filterValueProvider = t ->
                getValueProvider().apply(t);

        protected Column(ValueProvider<T, V> valueProvider,
                Renderer<? super V> renderer) {
            super(valueProvider, renderer);
        }

        public Column<T, V> setFilterable(boolean filterable) {
            this.filterable = filterable;
            getGrid().updateFilterHeader();
            return this;
        }

        public boolean isFilterable() {
            return filterable;
        }

        public Column<T, V> setFilterValueProvider(
                SerializableFunction<T, ? extends V> filterValueProvider) {
            this.filterValueProvider = filterValueProvider;
            return this;
        }

        public SerializableFunction<T, ? extends V> getFilterValueProvider() {
            return filterValueProvider;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected FilterGrid<T> getGrid() {
            return (FilterGrid) super.getGrid();
        }
    }

    private boolean filteringEnabled = true;
    private HeaderRow filterHeader;

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

    protected FilterGrid(PropertySet<T> propertySet) {
        super(propertySet);
    }

    public void setFilteringEnabled(boolean filteringEnabled) {
        this.filteringEnabled = filteringEnabled;
    }

    public boolean isFilteringEnabled() {
        return filteringEnabled;
    }

    private void updateFilterHeader() {
        if (filteringEnabled) {
            // TODO: 30/12/2017 Do not create new header each time
            filterHeader = appendHeaderRow();
            getColumnsStream().forEach(col -> {
                if (col.isFilterable()) {
                    // TODO: 30/12/2017 experimental filter field
                    AbstractField<String> filterField = new TextField();
                    filterHeader.getCell(col.getId()).setComponent(filterField);
                    filterField.addValueChangeListener(
                            event -> getDataProvider().refreshAll());
                }
            });
        } else if (filterHeader != null){
            removeHeaderRow(filterHeader);
            filterHeader = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Column<T, ?> getColumn(String columnId) {
        return (Column<T, ?>) super.getColumn(columnId);
    }

    @SuppressWarnings("unchecked")
    private Stream<Column<T, ?>> getColumnsStream() {
        return getColumns().stream().map(col -> (Column<T, ?>) col);
    }

    @Override
    protected <V> Column<T, V> createColumn(ValueProvider<T, V> valueProvider,
            AbstractRenderer<? super T, ? super V> renderer) {
        return new Column<>(valueProvider, renderer);
    }
}
