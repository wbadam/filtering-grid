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
import org.vaadin.addons.filteringgrid.filters.InMemoryFilter;

import com.vaadin.data.Binder.Binding;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.SerializableBiPredicate;
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

/**
 * Grid that handles filtering of items.
 *
 * @param <T>
 *         the grid bean type
 */
public class FilterGrid<T> extends Grid<T> {

    /**
     * A callback method for fetching filtered items. The callback is provided
     * with a list of sort orders, filter collection, offset index and limit.
     *
     * @param <T>
     *         the grid bean type
     */
    @FunctionalInterface
    public interface FetchFilteredItemsCallback<T> extends Serializable {

        /**
         * Returns a stream of items ordered by given sort orders, filtered by
         * given filters, limiting the results with given offset and limit.
         * <p>
         * This method is called after the size of the data set is asked from a
         * related size callback. The offset and limit are promised to be within
         * the size of the data set.
         *
         * @param sortOrder
         *         a list of sort orders
         * @param filters
         *         a filter collection
         * @param offset
         *         the first index to fetch
         * @param limit
         *         the fetched item count
         * @return stream of items
         */
        public Stream<T> fetchItems(List<QuerySortOrder> sortOrder,
                FilterCollection filters, int offset, int limit);
    }

    /**
     * A callback method for counting filtered items. The callback is provided
     * with a filter collection.
     */
    @FunctionalInterface
    public interface CountFilteredItemsCallback extends Serializable {
        /**
         * Returns the number of items filtered by given filters.
         *
         * @param filters
         *         a filter collection
         * @return item count
         */
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
            return (Column<T, V>) super
                    .setDescriptionGenerator(cellDescriptionGenerator);
        }

        @Override
        public Column<T, V> setDescriptionGenerator(
                DescriptionGenerator<T> cellDescriptionGenerator,
                ContentMode tooltipContentMode) {
            return (Column<T, V>) super
                    .setDescriptionGenerator(cellDescriptionGenerator,
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
            return (Column<T, V>) super
                    .setMinimumWidthFromContent(minimumWidthFromContent);
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
            return (Column<T, V>) super
                    .setHidingToggleCaption(hidingToggleCaption);
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
            return (Column<T, V>) super
                    .setEditorComponent(editorComponent, setter);
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
            return (Column<T, V>) super
                    .setRenderer(presentationProvider, renderer);
        }

        /**
         * Sets the given component as filter to the column and attaches it to
         * the column header.
         *
         * @param component
         *         the filtering component
         * @param filterPredicate
         *         the filter predicate
         * @param <F>
         *         the value type of the filtering component
         * @param <C>
         *         the filtering component's type
         * @return this column
         */
        public <F, C extends HasValue<F> & Component> Column<T, V> setFilter(
                C component, SerializableBiPredicate<V, F> filterPredicate) {
            getGrid().addFilter(InMemoryFilter
                    .wrapComponent(component, getValueProvider(),
                            filterPredicate), this);
            return this;
        }

        /**
         * Sets the given component as filter to the column and attaches it to
         * the column header.
         *
         * @param filterableValueProvider
         *         a value provider that converts the column's value to a value
         *         that the filter predicate expects
         * @param component
         *         the filtering component
         * @param filterPredicate
         *         the filter predicate
         * @param <F>
         *         the value type of the filtering component
         * @param <W>
         *         the filterable value type
         * @param <C>
         *         the filtering component's type
         * @return this column
         */
        public <F, W, C extends HasValue<F> & Component> Column<T, V> setFilter(
                ValueProvider<V, W> filterableValueProvider, C component,
                SerializableBiPredicate<W, F> filterPredicate) {
            getGrid().addFilter(InMemoryFilter.wrapComponent(component,
                    (T item) -> filterableValueProvider
                            .apply(getValueProvider().apply(item)),
                    filterPredicate), this);
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected FilterGrid<T> getGrid() {
            return (FilterGrid) super.getGrid();
        }
    }

    @SuppressWarnings("unchecked")
    private final SerializableFunction<Collection<Filter<?>>, SerializablePredicate<T>> filterConverter = filters -> item -> filters
            .stream().filter(InMemoryFilter.class::isInstance)
            .map(f -> (InMemoryFilter<T, ?, ?>) f).allMatch(f -> f.test(item));

    private final Collection<Filter<?>> filters = new HashSet<>();

    private final Map<Column<?, ?>, Filter<?>> columnFilters = new HashMap<>();

    private final Map<Filter<?>, Registration> filterRegistrations = new HashMap<>();

    private HeaderRow filterHeader;

    /**
     * Creates a new filtering grid without support for creating columns based
     * on property names. Use an alternative constructor, such as {@link
     * FilterGrid#FilterGrid(Class)}, to create a grid that automatically sets
     * up columns based on the type of presented data.
     *
     * @see #FilterGrid(Class)
     */
    public FilterGrid() {
        super();
    }

    /**
     * Creates a new filtering grid that uses reflection based on the provided
     * bean type to automatically set up an initial set of columns. All columns
     * will be configured using the same {@link Object#toString()} renderer that
     * is used by {@link #addColumn(ValueProvider)}.
     *
     * @param beanType
     *         the bean type to use, not <code>null</code>
     * @see #FilterGrid()
     */
    public FilterGrid(Class<T> beanType) {
        super(beanType);
    }

    /**
     * Creates a new {@code FilterGrid} using the given caption.
     *
     * @param caption
     *         the caption of the grid
     */
    public FilterGrid(String caption) {
        super(caption);
    }

    /**
     * Creates a new {@code FilterGrid} using the given caption and collection
     * of items.
     *
     * @param caption
     *         the caption of the grid
     * @param items
     *         the data items to use, not {@code null}
     */
    public FilterGrid(String caption, Collection<T> items) {
        super(caption);
        setItems(items);
    }

    /**
     * Sets a filterable in-memory data provider for this grid.
     *
     * @param dataProvider
     *         an in-memory data provider
     */
    public void setFilteredDataProvider(InMemoryDataProvider<T> dataProvider) {
        internalSetDataProvider(dataProvider.withConvertedFilter(
                filterConverter), filters);
    }

    /**
     * Sets a filterable callback data provider for this grid.
     *
     * @param fetchItems
     *         callback method for fetching items from the backend
     * @param sizeCallback
     *         callback method for counting items
     */
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

    private <C extends Filter<?> & Component> void addFilter(C filter,
            Column<?, ?> column) {
        addFilter(filter);
        columnFilters.put(column, filter);
        updateFilterHeader(column, filter);
    }

    /**
     * Adds a filter to the grid.
     *
     * @param filter
     *         the filter to add to the grid
     */
    public void addFilter(Filter<?> filter) {
        // Check if filter's key is unique
        if (filters.stream().map(Filter::getKey)
                .anyMatch(key -> key.equals(filter.getKey()))) {
            throw new IllegalArgumentException("Duplicate filter key");
        }

        filters.add(filter);
        filterRegistrations.put(filter, filter.addValueChangeListener(
                event -> getDataProvider().refreshAll()));
    }

    private <C extends Filter<?> & Component> void removeFilter(C filter,
            Column<?, ?> column) {
        removeFilter(filter);
        columnFilters.remove(column);
        updateFilterHeader(column, filter);
    }

    /**
     * Removes a filter from the grid.
     *
     * @param filter
     *         the filter to remove from the grid
     */
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

    private <C extends Filter<?> & Component> void updateFilterHeader(
            Column column, C filter) {
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
