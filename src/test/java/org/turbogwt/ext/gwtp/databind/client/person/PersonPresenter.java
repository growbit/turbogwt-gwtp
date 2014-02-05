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

package org.turbogwt.ext.gwtp.databind.client.person;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

import org.turbogwt.ext.gwtp.databind.client.DatabindView;
import org.turbogwt.mvp.databind.client.Binding;
import org.turbogwt.mvp.databind.client.BindingImpl;

/**
 * Presenter of an editing view of Person.
 *
 * @author Danilo Reinert
 */
public class PersonPresenter extends PresenterWidget<PersonPresenter.PersonView> implements PersonUiHandlers {

    interface PersonView extends DatabindView<PersonUiHandlers> {
    }

    private final Binding<Person> binding;

    @Inject
    public PersonPresenter(EventBus eventBus, PersonView view) {
        super(eventBus, view);

        // Initiate the binding with the view
        binding = new BindingImpl<Person>(view);

        // Set uiHandlers
        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        // Bind the properties
        registerHandler(binding.bind(PersonProperties.NAME, PersonProperties.NAME_ACCESSOR,
                PersonProperties.NAME_VALIDATOR));

        registerHandler(binding.bind(PersonProperties.BIRTHDAY, PersonProperties.BIRTHDAY_ACCESSOR,
                PersonProperties.BIRTHDAY_VALIDATOR));

        registerHandler(binding.bind(PersonProperties.PHONE_NUMBER, PersonProperties.PHONE_NUMBER_ACCESSOR,
                PersonProperties.PHONE_NUMBER_VALIDATOR, PersonProperties.PHONE_NUMBER_FORMATTER));

        registerHandler(binding.bind(PersonProperties.EMAIL, PersonProperties.EMAIL_ACCESSOR,
                PersonProperties.EMAIL_VALIDATOR));
    }

    @Override
    public void onValueChanged(String id, Object value) {
        // You must delegate #onValueChanged to the binding
        binding.onValueChanged(id, value);
    }

    public void edit(Person person) {
        binding.setModel(person);
    }

    public Person getPerson() {
        return binding.getModel();
    }

    void flush() {
        binding.flush();
    }

    void refresh() {
        binding.refresh();
    }
}
