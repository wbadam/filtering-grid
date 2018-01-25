package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public abstract class InMemoryFilterComponentWrapper<T, V, F, C extends Component & HasValue<F>> extends
        FilterComponentWrapper<F, C> implements InMemoryFilter<T, V, F> {

    public InMemoryFilterComponentWrapper(C component, Class<F> type) {
        super(component, type);
    }

    public InMemoryFilterComponentWrapper(String key, C component,
            Class<F> type) {
        super(key, component, type);
    }
}
