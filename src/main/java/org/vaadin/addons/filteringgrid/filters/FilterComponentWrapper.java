package org.vaadin.addons.filteringgrid.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jsoup.nodes.Element;

import com.vaadin.data.HasValue;
import com.vaadin.data.Validator;
import com.vaadin.server.ClientMethodInvocation;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Extension;
import com.vaadin.server.Resource;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.DesignContext;

import elemental.json.JsonObject;

public class FilterComponentWrapper<F, C extends Component & HasValue<F>> implements
        FilterComponent<F> {

    private final C component;
    private final String key;
    private final Class<F> type;

    protected FilterComponentWrapper(String key, C component, Class<F> type) {
        this.key = key;
        this.component = component;
        this.type = type;
    }

    @Override
    public F getValue() {
        return component.getValue();
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<F> listener) {
        return component.addValueChangeListener(listener);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setValue(F value) {
        component.setValue(value);
    }

    @Override
    public Class<F> getType() {
        return type;
    }

    public C getWrappedComponent() {
        return component;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        component.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return component.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        component.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return component.isReadOnly();
    }

    @Override
    public String getStyleName() {
        return component.getStyleName();
    }

    @Override
    public void setStyleName(String style) {
        component.setStyleName(style);
    }

    @Override
    public void addStyleName(String style) {
        component.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        component.removeStyleName(style);
    }

    @Override
    public String getPrimaryStyleName() {
        return component.getPrimaryStyleName();
    }

    @Override
    public void setPrimaryStyleName(String style) {
        component.setPrimaryStyleName(style);
    }

    @Override
    public boolean isEnabled() {
        return component.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        component.setEnabled(enabled);
    }

    @Override
    public boolean isVisible() {
        return component.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        component.setVisible(visible);
    }

    @Override
    public void setParent(HasComponents parent) {
        component.setParent(parent);
    }

    @Override
    public HasComponents getParent() {
        return component.getParent();
    }

    @Override
    public String getCaption() {
        return component.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        component.setCaption(caption);
    }

    @Override
    public Resource getIcon() {
        return component.getIcon();
    }

    @Override
    public void setIcon(Resource icon) {
        component.setIcon(icon);
    }

    @Override
    public UI getUI() {
        return component.getUI();
    }

    @Override
    public void attach() {
        component.attach();
    }

    @Override
    public Locale getLocale() {
        return component.getLocale();
    }

    @Override
    public void setId(String id) {
        component.setId(id);
    }

    @Override
    public String getId() {
        return component.getId();
    }

    @Override
    public String getDescription() {
        return component.getDescription();
    }

    @Override
    public void readDesign(Element design, DesignContext designContext) {
        component.readDesign(design, designContext);
    }

    @Override
    public void writeDesign(Element design, DesignContext designContext) {
        component.writeDesign(design, designContext);
    }

    @Override
    public Registration addListener(Listener listener) {
        return component.addListener(listener);
    }

    @Override
    @Deprecated
    public void removeListener(Listener listener) {
        component.removeListener(listener);
    }

    @Override
    public Registration addAttachListener(AttachListener listener) {
        return component.addAttachListener(listener);
    }

    @Override
    @Deprecated
    public void removeAttachListener(AttachListener listener) {
        component.removeAttachListener(listener);
    }

    @Override
    public Registration addDetachListener(DetachListener listener) {
        return component.addDetachListener(listener);
    }

    @Override
    @Deprecated
    public void removeDetachListener(DetachListener listener) {
        component.removeDetachListener(listener);
    }

    @Override
    public List<ClientMethodInvocation> retrievePendingRpcCalls() {
        return component.retrievePendingRpcCalls();
    }

    @Override
    public boolean isConnectorEnabled() {
        return component.isConnectorEnabled();
    }

    @Override
    public Class<? extends SharedState> getStateType() {
        return component.getStateType();
    }

    @Override
    @Deprecated
    public void requestRepaint() {
        component.requestRepaint();
    }

    @Override
    public void markAsDirty() {
        component.markAsDirty();
    }

    @Override
    @Deprecated
    public void requestRepaintAll() {
        component.requestRepaintAll();
    }

    @Override
    public void markAsDirtyRecursive() {
        component.markAsDirtyRecursive();
    }

    @Override
    public boolean isAttached() {
        return component.isAttached();
    }

    @Override
    public void detach() {
        component.detach();
    }

    @Override
    public Collection<Extension> getExtensions() {
        return component.getExtensions();
    }

    @Override
    public void removeExtension(Extension extension) {
        component.removeExtension(extension);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        component.beforeClientResponse(initial);
    }

    @Override
    public JsonObject encodeState() {
        return component.encodeState();
    }

    @Override
    public boolean handleConnectorRequest(VaadinRequest request,
            VaadinResponse response, String path) throws IOException {
        return component.handleConnectorRequest(request, response, path);
    }

    @Override
    public ServerRpcManager<?> getRpcManager(String rpcInterfaceName) {
        return component.getRpcManager(rpcInterfaceName);
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return component.getErrorHandler();
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        component.setErrorHandler(errorHandler);
    }

    @Override
    public float getWidth() {
        return component.getWidth();
    }

    @Override
    public float getHeight() {
        return component.getHeight();
    }

    @Override
    public Unit getWidthUnits() {
        return component.getWidthUnits();
    }

    @Override
    public Unit getHeightUnits() {
        return component.getHeightUnits();
    }

    @Override
    public void setHeight(String height) {
        component.setHeight(height);
    }

    @Override
    public void setWidth(float width, Unit unit) {
        component.setWidth(width, unit);
    }

    @Override
    public void setHeight(float height, Unit unit) {
        component.setHeight(height, unit);
    }

    @Override
    public void setWidth(String width) {
        component.setWidth(width);
    }

    @Override
    public void setSizeFull() {
        component.setSizeFull();
    }

    @Override
    public void setSizeUndefined() {
        component.setSizeUndefined();
    }

    @Override
    public void setWidthUndefined() {
        component.setWidthUndefined();
    }

    @Override
    public void setHeightUndefined() {
        component.setHeightUndefined();
    }

    @Override
    public String getConnectorId() {
        return component.getConnectorId();
    }

    @Override
    public F getEmptyValue() {
        return component.getEmptyValue();
    }

    @Override
    public Optional<F> getOptionalValue() {
        return component.getOptionalValue();
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Override
    public void clear() {
        component.clear();
    }

    @Override
    public Validator<F> getDefaultValidator() {
        return component.getDefaultValidator();
    }

    @Override
    public void addStyleNames(String... styles) {
        component.addStyleNames(styles);
    }

    @Override
    public void removeStyleNames(String... styles) {
        component.removeStyleNames(styles);
    }
}
