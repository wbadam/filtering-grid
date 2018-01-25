package org.vaadin.addons.filteringgrid.filters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableBiPredicate;
import com.vaadin.ui.DateField;

public class DateFilter<T> extends
        InMemoryFilterComponentWrapper<T, Date, LocalDate, DateField> {

    private ValueProvider<T, Date> valueProvider;
    private SerializableBiPredicate<Date, Date> filterPredicate;
    private ZoneId zoneId;

    public DateFilter(ValueProvider<T, Date> valueProvider,
            SerializableBiPredicate<Date, Date> filterPredicate) {
        this(valueProvider, filterPredicate, ZoneId.systemDefault());
    }

    public DateFilter(ValueProvider<T, Date> valueProvider,
            SerializableBiPredicate<Date, Date> filterPredicate,
            ZoneId zoneId) {
        super(new DateField(), LocalDate.class);
        this.valueProvider = valueProvider;
        this.filterPredicate = filterPredicate;
        this.zoneId = zoneId;
    }

    @Override
    public ValueProvider<T, Date> getValueProvider() {
        return valueProvider;
    }

    @Override
    public SerializableBiPredicate<Date, LocalDate> getFilterPredicate() {
        return (value, filterValue) -> filterValue == null || filterPredicate
                .test(value, Date.from(
                        filterValue.atStartOfDay(zoneId).toInstant()));
    }
}
