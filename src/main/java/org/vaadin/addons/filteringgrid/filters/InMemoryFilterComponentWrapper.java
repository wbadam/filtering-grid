package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public abstract class InMemoryFilterComponentWrapper<T, V, F, C extends Component & HasValue<F>> extends
        FilterComponentWrapper<F, C> implements InMemoryFilter<T, V, F> {

    public InMemoryFilterComponentWrapper(C component) {
        super(component);
    }

    public InMemoryFilterComponentWrapper(String key, C component) {
        super(key, component);
    }
}
