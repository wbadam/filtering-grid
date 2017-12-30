package org.vaadin.addons.filteringgrid;

import com.vaadin.ui.Component;
import org.vaadin.addons.filteringgrid.jsexample.MyJavaScriptComponent;
import org.vaadin.addonhelpers.AbstractTest;

public class BasicJavaScriptComponentUsageUI extends AbstractTest {

    public static final String INITIAL_VALUE = "Foo!";

    @Override
    public Component getTestComponent() {
        MyJavaScriptComponent c = new MyJavaScriptComponent();
        c.setValue(INITIAL_VALUE);
        return c;
    }


}
