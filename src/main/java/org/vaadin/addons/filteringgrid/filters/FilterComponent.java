package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public interface FilterComponent<F> extends Filter<F>, Component, HasValue<F> {

    static <F, C extends Component & HasValue<F>> FilterComponent<F> createFromComponent(
            C comp, String key, Class<F> type) {
        return new AbstractFilterComponent<F, C>(comp) {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public Class<F> getType() {
                return type;
            }
        };
    }
}
