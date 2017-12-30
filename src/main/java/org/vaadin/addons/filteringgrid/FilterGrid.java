package org.vaadin.addons.filteringgrid;

import java.util.Collection;
import java.util.stream.Stream;

import com.vaadin.data.PropertySet;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.server.SerializableFunction;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.AbstractRenderer;
import com.vaadin.ui.renderers.Renderer;

public class FilterGrid<T> extends Grid<T> {

    public static class Column<T, V, FV, FFV> extends Grid.Column<T, V> {

        private boolean filterable;

        @SuppressWarnings("unchecked")
        private SerializableFunction<T, FV> filteringValueProvider = t ->
                (FV) getValueProvider().apply(t);

        private AbstractField<FFV> filterField;

//        private SerializablePredicate<String> filterPredicate = value -> "String".contains(value);
        private SerializableBiPredicate<FFV, FV> filterPredicate = (filterFieldValue, filterableValue) -> filterableValue.equals(filterFieldValue);

        protected Column(ValueProvider<T, V> valueProvider,
                Renderer<? super V> renderer) {
            super(valueProvider, renderer);
        }

        public Column<T, V, FV, FFV> setFilterable(boolean filterable) {
            this.filterable = filterable;
            getGrid().updateFilterHeader();
            return this;
        }

        public boolean isFilterable() {
            return filterable;
        }

        public Column<T, V, FV, FFV> setFilteringValueProvider(
                SerializableFunction<T, FV> filteringValueProvider) {
            this.filteringValueProvider = filteringValueProvider;
            return this;
        }

        public SerializableFunction<T, FV> getFilteringValueProvider() {
            return filteringValueProvider;
        }

        public void setFilterField(AbstractField<FFV> filterField) {
            this.filterField = filterField;
        }

        public AbstractField<FFV> getFilterField() {
            return filterField;
        }

        public void setFilterPredicate(
                SerializableBiPredicate<FFV, FV> filterPredicate) {
            this.filterPredicate = filterPredicate;
        }

        public SerializableBiPredicate<FFV, FV> getFilterPredicate() {
            return filterPredicate;
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
    public Column<T, ?, ?, ?> getColumn(String columnId) {
        return (Column<T, ?, ?, ?>) super.getColumn(columnId);
    }

    @SuppressWarnings("unchecked")
    private <FV, FFV> Stream<Column<T, ?, FV, FFV>> getColumnsStream() {
        return getColumns().stream().map(col -> (Column<T, ?, FV, FFV>) col);
    }

    @Override
    protected <V> Column<T, V, ?, ?> createColumn(ValueProvider<T, V> valueProvider,
            AbstractRenderer<? super T, ? super V> renderer) {
        return new Column<>(valueProvider, renderer);
    }

    private void setFilter() {
        if (getDataProvider() instanceof ConfigurableFilterDataProvider) {
            ((ConfigurableFilterDataProvider) getDataProvider())
                    .setFilter((SerializablePredicate<T>) item -> {
                                if (filteringEnabled) {
//                                    return getColumnsStream().filter(Column::isFilterable).anyMatch(col -> {
//                                        return ((String) col.getFilteringValueProvider().apply(item)).contains(
//                                                ((TextField) filterHeader
//                                                        .getCell(col.getId())
//                                                        .getComponent()).getValue());
//                                    });
//                                    return getColumnsStream().filter(Column::isFilterable).anyMatch(col -> {
//                                        return col.filterPredicate.test(col.filterField.getValue(), col.filteringValueProvider.apply(item));
//                                        }
                                    return getColumnsStream().filter(Column::isFilterable).anyMatch(col -> {
//                                        return ((String) col.getFilteringValueProvider().apply(item)).contains(
//                                                ((TextField) filterHeader
//                                                        .getCell(col.getId())
//                                                        .getComponent()).getValue());
                                        return col.filterPredicate.test(col.filterField.getValue(), col.filteringValueProvider
                                                .apply(item));
                                    });
                                }
                                return true;
                            }
                    );
        }
    }
}
