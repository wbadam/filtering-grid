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

public abstract class AbstractFilterComponent<F, C extends Component & HasValue<F>> implements
        FilterComponent<F> {

    private final C filterComponent;

    public AbstractFilterComponent(C component) {
        filterComponent = component;
    }

    @Override
    public F getValue() {
        return filterComponent.getValue();
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<F> listener) {
        return filterComponent.addValueChangeListener(listener);
    }

    @Override
    public void setValue(F value) {
        filterComponent.setValue(value);
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        filterComponent.setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return filterComponent.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        filterComponent.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return filterComponent.isReadOnly();
    }

    @Override
    public String getStyleName() {
        return filterComponent.getStyleName();
    }

    @Override
    public void setStyleName(String style) {
        filterComponent.setStyleName(style);
    }

    @Override
    public void addStyleName(String style) {
        filterComponent.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        filterComponent.removeStyleName(style);
    }

    @Override
    public String getPrimaryStyleName() {
        return filterComponent.getPrimaryStyleName();
    }

    @Override
    public void setPrimaryStyleName(String style) {
        filterComponent.setPrimaryStyleName(style);
    }

    @Override
    public boolean isEnabled() {
        return filterComponent.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        filterComponent.setEnabled(enabled);
    }

    @Override
    public boolean isVisible() {
        return filterComponent.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        filterComponent.setVisible(visible);
    }

    @Override
    public void setParent(HasComponents parent) {
        filterComponent.setParent(parent);
    }

    @Override
    public HasComponents getParent() {
        return filterComponent.getParent();
    }

    @Override
    public String getCaption() {
        return filterComponent.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        filterComponent.setCaption(caption);
    }

    @Override
    public Resource getIcon() {
        return filterComponent.getIcon();
    }

    @Override
    public void setIcon(Resource icon) {
        filterComponent.setIcon(icon);
    }

    @Override
    public UI getUI() {
        return filterComponent.getUI();
    }

    @Override
    public void attach() {
        filterComponent.attach();
    }

    @Override
    public Locale getLocale() {
        return filterComponent.getLocale();
    }

    @Override
    public void setId(String id) {
        filterComponent.setId(id);
    }

    @Override
    public String getId() {
        return filterComponent.getId();
    }

    @Override
    public String getDescription() {
        return filterComponent.getDescription();
    }

    @Override
    public void readDesign(Element design, DesignContext designContext) {
        filterComponent.readDesign(design, designContext);
    }

    @Override
    public void writeDesign(Element design, DesignContext designContext) {
        filterComponent.writeDesign(design, designContext);
    }

    @Override
    public Registration addListener(Listener listener) {
        return filterComponent.addListener(listener);
    }

    @Override
    @Deprecated
    public void removeListener(Listener listener) {
        filterComponent.removeListener(listener);
    }

    @Override
    public Registration addAttachListener(AttachListener listener) {
        return filterComponent.addAttachListener(listener);
    }

    @Override
    @Deprecated
    public void removeAttachListener(AttachListener listener) {
        filterComponent.removeAttachListener(listener);
    }

    @Override
    public Registration addDetachListener(DetachListener listener) {
        return filterComponent.addDetachListener(listener);
    }

    @Override
    @Deprecated
    public void removeDetachListener(DetachListener listener) {
        filterComponent.removeDetachListener(listener);
    }

    @Override
    public List<ClientMethodInvocation> retrievePendingRpcCalls() {
        return filterComponent.retrievePendingRpcCalls();
    }

    @Override
    public boolean isConnectorEnabled() {
        return filterComponent.isConnectorEnabled();
    }

    @Override
    public Class<? extends SharedState> getStateType() {
        return filterComponent.getStateType();
    }

    @Override
    @Deprecated
    public void requestRepaint() {
        filterComponent.requestRepaint();
    }

    @Override
    public void markAsDirty() {
        filterComponent.markAsDirty();
    }

    @Override
    @Deprecated
    public void requestRepaintAll() {
        filterComponent.requestRepaintAll();
    }

    @Override
    public void markAsDirtyRecursive() {
        filterComponent.markAsDirtyRecursive();
    }

    @Override
    public boolean isAttached() {
        return filterComponent.isAttached();
    }

    @Override
    public void detach() {
        filterComponent.detach();
    }

    @Override
    public Collection<Extension> getExtensions() {
        return filterComponent.getExtensions();
    }

    @Override
    public void removeExtension(Extension extension) {
        filterComponent.removeExtension(extension);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        filterComponent.beforeClientResponse(initial);
    }

    @Override
    public JsonObject encodeState() {
        return filterComponent.encodeState();
    }

    @Override
    public boolean handleConnectorRequest(VaadinRequest request,
            VaadinResponse response, String path) throws IOException {
        return filterComponent.handleConnectorRequest(request, response, path);
    }

    @Override
    public ServerRpcManager<?> getRpcManager(String rpcInterfaceName) {
        return filterComponent.getRpcManager(rpcInterfaceName);
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return filterComponent.getErrorHandler();
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        filterComponent.setErrorHandler(errorHandler);
    }

    @Override
    public float getWidth() {
        return filterComponent.getWidth();
    }

    @Override
    public float getHeight() {
        return filterComponent.getHeight();
    }

    @Override
    public Unit getWidthUnits() {
        return filterComponent.getWidthUnits();
    }

    @Override
    public Unit getHeightUnits() {
        return filterComponent.getHeightUnits();
    }

    @Override
    public void setHeight(String height) {
        filterComponent.setHeight(height);
    }

    @Override
    public void setWidth(float width, Unit unit) {
        filterComponent.setWidth(width, unit);
    }

    @Override
    public void setHeight(float height, Unit unit) {
        filterComponent.setHeight(height, unit);
    }

    @Override
    public void setWidth(String width) {
        filterComponent.setWidth(width);
    }

    @Override
    public void setSizeFull() {
        filterComponent.setSizeFull();
    }

    @Override
    public void setSizeUndefined() {
        filterComponent.setSizeUndefined();
    }

    @Override
    public void setWidthUndefined() {
        filterComponent.setWidthUndefined();
    }

    @Override
    public void setHeightUndefined() {
        filterComponent.setHeightUndefined();
    }

    @Override
    public String getConnectorId() {
        return filterComponent.getConnectorId();
    }

    @Override
    public F getEmptyValue() {
        return filterComponent.getEmptyValue();
    }

    @Override
    public Optional<F> getOptionalValue() {
        return filterComponent.getOptionalValue();
    }

    @Override
    public boolean isEmpty() {
        return filterComponent.isEmpty();
    }

    @Override
    public void clear() {
        filterComponent.clear();
    }

    @Override
    public Validator<F> getDefaultValidator() {
        return filterComponent.getDefaultValidator();
    }

    @Override
    public void addStyleNames(String... styles) {
        filterComponent.addStyleNames(styles);
    }

    @Override
    public void removeStyleNames(String... styles) {
        filterComponent.removeStyleNames(styles);
    }
}
