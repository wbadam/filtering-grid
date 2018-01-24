package org.vaadin.addons.filteringgrid.filters;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

public class StringFilter<C extends Component & HasValue<String>> extends
        FilterComponentWrapper<String, C> {

    public StringFilter(String key, C component) {
        super(key, component, String.class);
    }
}
