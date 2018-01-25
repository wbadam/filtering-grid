package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public interface FilterComponent<F> extends Filter<F>, Component {

    static <F, C extends Component & HasValue<F>> FilterComponent<F> createFromComponent(
            C component, String key, Class<F> type) {
        return new FilterComponentWrapper<>(key, component, type);
    }
}
