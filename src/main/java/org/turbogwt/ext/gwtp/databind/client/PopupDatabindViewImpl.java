/*
 * Copyright 2014 Grow Bit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.turbogwt.ext.gwtp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import javax.annotation.Nullable;

import org.turbogwt.core.util.Registration;
import org.turbogwt.mvp.databind.DatabindUiHandler;
import org.turbogwt.mvp.databind.DatabindViewEngine;
import org.turbogwt.mvp.databind.Strategy;
import org.turbogwt.mvp.databind.validation.ValidationMessage;

/**
 * Base class for a {@link PopupDatabindView}.
 * You should always call {@link #setUiHandlers(com.gwtplatform.mvp.client.UiHandlers)} from your
 * presenter 's constructor.
 * <p/>
 * <b>Important!</b> Never call {@link #getUiHandlers()} inside your constructor
 * since the {@link com.gwtplatform.mvp.client.UiHandlers} are not yet set.
 *
 * @param <H> Your {@link DatabindUiHandlers} interface type.
 * @author Danilo Reinert
 */
public class PopupDatabindViewImpl<H extends DatabindUiHandlers> extends PopupViewWithUiHandlers<H>
        implements PopupDatabindView<H> {

    private final DatabindViewEngine engine = new DatabindViewEngine();

    /**
     * The {@link PopupViewWithUiHandlers} class uses the {@link EventBus} to listen to {@link
     * com.gwtplatform.mvp.client.proxy.NavigationEvent} in order to automatically close when this event is fired, if
     * desired. See {@link #setAutoHideOnNavigationEventEnabled(boolean)} for details.
     *
     * @param eventBus The {@link EventBus}.
     */
    protected PopupDatabindViewImpl(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public <F> Registration bind(String id, HasValue<F> widget, Strategy strategy) {
        return engine.bind(id, widget, strategy);
    }

    @Override
    public <F> Registration bind(String id, TakesValue<F> widget) {
        return engine.bind(id, widget);
    }

    @Override
    public <F> F getValue(String id) {
        return engine.getValue(id);
    }

    /**
     * You should override this method if you want to handle failed validation events.
     * It happens when the presenter flushes data from view.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    @Override
    public void onValidationFailure(String property, @Nullable ValidationMessage message) {
    }

    /**
     * You should override this method if you want to handle successful validation events.
     * It happens when the presenter flushes data from view.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    @Override
    public void onValidationSuccess(String property, @Nullable ValidationMessage message) {
    }

    /**
     * This method sets only the DatabindUiHandlers.
     * If you want to set both DatanbindUiHandlers and View's UiHandlers please use #setUiHandlers.
     *
     * @param uiHandlers The handler of the databind mechanism
     */
    @Override
    public void setDatabindUiHandler(DatabindUiHandler uiHandlers) {
        engine.setDatabindUiHandler(uiHandlers);
    }

    /**
     * This method sets both View's UiHandlers and DatabindUiHandlers.
     *
     * @param uiHandlers The handler of view and databind mechanism
     */
    @Override
    public void setUiHandlers(H uiHandlers) {
        super.setUiHandlers(uiHandlers);
        engine.setDatabindUiHandler(uiHandlers);
    }

    @Override
    public <F> void setValue(String id, F value) {
        engine.setValue(id, value);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }
}
