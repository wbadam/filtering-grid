package org.vaadin.addons.filteringgrid.components;

import java.time.LocalDate;
import java.util.Locale;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class DateRangeField extends CustomComponent implements
        HasValue<DateRangeField.DateRange> {

    public static class DateRange {
        private LocalDate dateFrom;
        private LocalDate dateTo;

        public DateRange(LocalDate dateFrom, LocalDate dateTo) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        public void setDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
        }
    }

    private final HorizontalLayout container;
    private final DateField dateFieldFrom;
    private final DateField dateFieldTo;
    private boolean readOnly;

    public DateRangeField() {
        dateFieldFrom = new DateField();
        dateFieldTo = new DateField();
        container = new HorizontalLayout(dateFieldFrom, new Label("-"),
                dateFieldTo);
        setCompositionRoot(container);

        dateFieldFrom.addValueChangeListener(event -> setValue(new DateRange
                (event.getValue(), dateFieldTo.getValue())));
        dateFieldTo.addValueChangeListener(event -> setValue(new DateRange
                (dateFieldFrom.getValue(), event.getValue())));
    }

    @Override
    public DateRange getValue() {
        return new DateRange(dateFieldFrom.getValue(), dateFieldTo.getValue());
    }

    @Override
    public void setValue(DateRange value) {
        dateFieldFrom.setValue(value.dateFrom);
        dateFieldTo.setValue(value.dateTo);
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<DateRange> listener) {
        return addListener(ValueChangeEvent.class, listener,
                ValueChangeListener.VALUE_CHANGE_METHOD);
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        dateFieldFrom.setReadOnly(readOnly);
        dateFieldTo.setReadOnly(readOnly);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        // NOOP
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);

        dateFieldFrom.setLocale(locale);
        dateFieldTo.setLocale(locale);
    }
}
