package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public interface FilterComponent<F> extends Filter<F>, Component {

    public static <F, C extends Component & HasValue<F>> FilterComponent<F> createFromComponent(
            C component, String key) {
        return new FilterComponentWrapper<>(key, component);
    }
}
