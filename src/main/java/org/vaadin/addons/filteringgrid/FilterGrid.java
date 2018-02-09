package org.vaadin.addons.filteringgrid;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.vaadin.addons.filteringgrid.filters.Filter;
import org.vaadin.addons.filteringgrid.filters.FilterCollection;
import org.vaadin.addons.filteringgrid.filters.FilterComponent;
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

import com.vaadin.data.Binder.Binding;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializableFunction;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.Setter;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.DescriptionGenerator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.SortOrderProvider;
import com.vaadin.ui.renderers.AbstractRenderer;
import com.vaadin.ui.renderers.Renderer;

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

    public static class Column<T, V> extends Grid.Column<T, V> {

        private SerializableFunction<T, V> filterValueProvider = t ->
                getValueProvider().apply(t);

        protected Column(ValueProvider<T, V> valueProvider,
                Renderer<? super V> renderer) {
            super(valueProvider, renderer);
        }

        protected <P> Column(ValueProvider<T, V> valueProvider,
                ValueProvider<V, P> presentationProvider,
                Renderer<? super P> renderer) {
            super(valueProvider, presentationProvider, renderer);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Column<T, V> setId(String id) {
            return (Column<T, V>) super.setId(id);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Column<T, V> setSortable(boolean sortable) {
            return (Column<T, V>) super.setSortable(sortable);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Column<T, V> setAssistiveCaption(String caption) {
            return (Column<T, V>) super.setAssistiveCaption(caption);
        }

        @Override
        public Column<T, V> setCaption(String caption) {
            return (Column<T, V>) super.setCaption(caption);
        }

        @Override
        public Column<T, V> setComparator(
                SerializableComparator<T> comparator) {
            return (Column<T, V>) super.setComparator(comparator);
        }

        @Override
        public Column<T, V> setSortProperty(String... properties) {
            return (Column<T, V>) super.setSortProperty(properties);
        }

        @Override
        public Column<T, V> setSortOrderProvider(SortOrderProvider provider) {
            return (Column<T, V>) super.setSortOrderProvider(provider);
        }

        @Override
        public Column<T, V> setStyleGenerator(
                StyleGenerator<T> cellStyleGenerator) {
            return (Column<T, V>) super.setStyleGenerator(cellStyleGenerator);
        }

        @Override
        public Column<T, V> setDescriptionGenerator(
                DescriptionGenerator<T> cellDescriptionGenerator) {
            return (Column<T, V>) super.setDescriptionGenerator(cellDescriptionGenerator);
        }

        @Override
        public Column<T, V> setDescriptionGenerator(
                DescriptionGenerator<T> cellDescriptionGenerator,
                ContentMode tooltipContentMode) {
            return (Column<T, V>) super.setDescriptionGenerator(cellDescriptionGenerator,
                    tooltipContentMode);
        }

        @Override
        public Column<T, V> setExpandRatio(int expandRatio) throws
                IllegalStateException {
            return (Column<T, V>) super.setExpandRatio(expandRatio);
        }

        @Override
        public Column<T, V> clearExpandRatio() throws
                IllegalStateException {
            return (Column<T, V>) super.clearExpandRatio();
        }

        @Override
        public Column<T, V> setWidth(double pixelWidth) throws
                IllegalStateException, IllegalArgumentException {
            return (Column<T, V>) super.setWidth(pixelWidth);
        }

        @Override
        public Column<T, V> setWidthUndefined() {
            return (Column<T, V>) super.setWidthUndefined();
        }

        @Override
        public Column<T, V> setMinimumWidth(double pixels) throws
                IllegalStateException {
            return (Column<T, V>) super.setMinimumWidth(pixels);
        }

        @Override
        public Column<T, V> setMinimumWidthFromContent(
                boolean minimumWidthFromContent) throws IllegalStateException {
            return (Column<T, V>) super.setMinimumWidthFromContent(minimumWidthFromContent);
        }

        @Override
        public Column<T, V> setMaximumWidth(double pixels) {
            return (Column<T, V>) super.setMaximumWidth(pixels);
        }

        @Override
        public Column<T, V> setResizable(boolean resizable) {
            return (Column<T, V>) super.setResizable(resizable);
        }

        @Override
        public Column<T, V> setHidingToggleCaption(
                String hidingToggleCaption) {
            return (Column<T, V>) super.setHidingToggleCaption(hidingToggleCaption);
        }

        @Override
        public Column<T, V> setHidden(boolean hidden) {
            return (Column<T, V>) super.setHidden(hidden);
        }

        @Override
        public Column<T, V> setHidable(boolean hidable) {
            return (Column<T, V>) super.setHidable(hidable);
        }

        @Override
        public Column<T, V> setEditable(boolean editable) {
            return (Column<T, V>) super.setEditable(editable);
        }

        @Override
        public Column<T, V> setEditorBinding(Binding<T, ?> binding) {
            return (Column<T, V>) super.setEditorBinding(binding);
        }

        @Override
        public <C extends HasValue<V> & Component> Column<T, V> setEditorComponent(
                C editorComponent, Setter<T, V> setter) {
            return (Column<T, V>) super.setEditorComponent(editorComponent, setter);
        }

        @Override
        public <F, C extends HasValue<F> & Component> Column<T, V> setEditorComponent(
                C editorComponent) {
            return (Column<T, V>) super.setEditorComponent(editorComponent);
        }

        @Override
        public Column<T, V> setRenderer(Renderer<? super V> renderer) {
            return (Column<T, V>) super.setRenderer(renderer);
        }

        @Override
        public <P> Column<T, V> setRenderer(
                ValueProvider<V, P> presentationProvider,
                Renderer<? super P> renderer) {
            return (Column<T, V>) super.setRenderer(presentationProvider, renderer);
        }

        public <F> Column<T, V> setFilter(FilterComponent<F> filter) {
            getGrid().addFilter(filter, this);
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected FilterGrid<T> getGrid() {
            return (FilterGrid) super.getGrid();
        }
    }

    private final SerializableFunction<Collection<Filter<?>>, SerializablePredicate<T>> filterConverter = filters -> item -> filters
            .stream().filter(f -> f instanceof InMemoryFilter)
            .map(f -> (InMemoryFilter<T, ?, ?>) f).allMatch(f -> f.test(item));

    private final Collection<Filter<?>> filters = new HashSet<>();

    private final Map<Column<?, ?>, Filter<?>> columnFilters = new HashMap<>();

    private final Map<Filter<?>, Registration> filterRegistrations = new HashMap<>();

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

    private void addFilter(FilterComponent<?> filter, Column<?, ?> column) {
        addFilter(filter);
        columnFilters.put(column, filter);
        updateFilterHeader(column, filter);
    }

    public void addFilter(Filter<?> filter) {
        filters.add(filter);
        filterRegistrations.put(filter, filter.addValueChangeListener(
                event -> getDataProvider().refreshAll()));
    }

    private void removeFilter(FilterComponent<?> filter, Column<?, ?> column) {
        removeFilter(filter);
        columnFilters.remove(column);
        updateFilterHeader(column, filter);
    }

    public void removeFilter(Filter<?> filter) {
        filters.remove(filter);
        Optional.ofNullable(filterRegistrations.remove(filter))
                .ifPresent(Registration::remove);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Column<T, ?> addColumn(String propertyName) {
        return (Column<T, ?>) super.addColumn(propertyName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Column<T, ?> addColumn(String propertyName,
            AbstractRenderer<? super T, ?> renderer) {
        return (Column<T, ?>) super.addColumn(propertyName, renderer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> Column<T, V> addColumn(ValueProvider<T, V> valueProvider) {
        return (Column<T, V>) super.addColumn(valueProvider);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> Column<T, V> addColumn(ValueProvider<T, V> valueProvider,
            AbstractRenderer<? super T, ? super V> renderer) {
        return (Column<T, V>) super.addColumn(valueProvider, renderer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> Column<T, V> addColumn(ValueProvider<T, V> valueProvider,
            ValueProvider<V, String> presentationProvider) {
        return (Column<T, V>) super
                .addColumn(valueProvider, presentationProvider);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V, P> Column<T, V> addColumn(ValueProvider<T, V> valueProvider,
            ValueProvider<V, P> presentationProvider,
            AbstractRenderer<? super T, ? super P> renderer) {
        return (Column<T, V>) super
                .addColumn(valueProvider, presentationProvider, renderer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends Component> Column<T, V> addComponentColumn(
            ValueProvider<T, V> componentProvider) {
        return (Column<T, V>) super.addComponentColumn(componentProvider);
    }

    @Override
    protected <V, P> Column<T, V> createColumn(
            ValueProvider<T, V> valueProvider,
            ValueProvider<V, P> presentationProvider,
            AbstractRenderer<? super T, ? super P> renderer) {
        return new Column<>(valueProvider, presentationProvider, renderer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Column<T, ?> getColumn(String columnId) {
        return (Column<T, ?>) super.getColumn(columnId);
    }

    private void updateFilterHeader(Column column, FilterComponent filter) {
        if (!columnFilters.isEmpty()) {
            if (filterHeader == null) {
                filterHeader = appendHeaderRow();
            }
            filterHeader.getCell(column).setComponent(filter);
        } else {
            removeHeaderRow(filterHeader);
        }
    }
}
