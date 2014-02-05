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

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import java.util.Date;

/**
 * @author Danilo Reinert
 */
public class PersonGwtTestCase extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "org.turbogwt.ext.gwtp.databind.DatabindTest";
    }

    public void testValueChangesAndFormatting() {
        // Create the event bus
        final EventBus eventBus = GWT.create(SimpleEventBus.class);

        // Create view
        final PersonViewImpl view = GWT.create(PersonViewImpl.class);

        // Create presenter (and bind)
        final PersonPresenter presenter = new PersonPresenter(eventBus, view);
        presenter.onBind();

        // Create model
        final String name = "John Doe";
        final double birthdayTime = 318394800000.0;
        final int phoneNumber = 55512345;
        final String email = "john@doe.com";

        final Person model = new Person();
        model.setName(name);
        model.setBirthday(new Date((long) birthdayTime));
        model.setPhoneNumber(phoneNumber);
        model.setEmail(email);

        // Start editing
        presenter.edit(model);

        // Check values at view
        assertEquals(name, view.name.getValue());
        assertEquals(birthdayTime, (double) view.birthday.getValue().getTime());
        assertEquals(PersonProperties.PHONE_NUMBER_FORMATTER.format(phoneNumber), view.phoneNumber.getValue());
        assertEquals(email, view.email.getValue());

        // User input new values
        final String name2 = "Alice";
        final double birthdayTime2 = 603255600000.0;
        final String phoneNumber2 = "505-98765";
        final String email2 = "alice@alice.org";

        view.name.setValue(name2, true);
        view.birthday.setValue(new Date((long) birthdayTime2), true);
        view.phoneNumber.setValue(phoneNumber2, true);
        view.email.setValue(email2, true);

        // Check new values at presenter
        assertEquals(name2, presenter.getPerson().getName());
        assertEquals(birthdayTime2, (double) presenter.getPerson().getBirthday().getTime());
        assertEquals(PersonProperties.PHONE_NUMBER_FORMATTER.unformat(phoneNumber2),
                presenter.getPerson().getPhoneNumber());
        assertEquals(email2, presenter.getPerson().getEmail());
    }

    public void testValidations() {
        // Create the event bus
        final EventBus eventBus = GWT.create(SimpleEventBus.class);

        // Create view (all properties initially set as valid)
        final PersonViewImpl view = GWT.create(PersonViewImpl.class);

        // Create presenter (and bind)
        final PersonPresenter presenter = new PersonPresenter(eventBus, view);
        presenter.onBind();

        // Create model
        final Person model = new Person();

        // Start editing
        presenter.edit(model);

        // Flush current values from view.
        // It will cause validation failures since there are only empty values in view
        // and all properties are required according to its validators.
        presenter.flush();

        // Check if onValidationFailure was called
        assertFalse(view.nameValid);
        assertFalse(view.birthdayValid);
        assertFalse(view.phoneNumberValid);
        assertFalse(view.emailValid);

        // User input valid values
        view.name.setValue("John Doe", true);
        view.birthday.setValue(new Date(318394800000L), true);
        view.phoneNumber.setValue("555-12345", true);
        view.email.setValue("john@doe.com", true);

        // Check if onValidationSuccess was called
        assertTrue(view.nameValid);
        assertTrue(view.birthdayValid);
        assertTrue(view.phoneNumberValid);
        assertTrue(view.emailValid);

        // User input invalid values
        view.name.setValue(null, true);
        view.birthday.setValue(null, true);
        view.phoneNumber.setValue("", true);
        view.email.setValue("invalid.email", true);

        // Check if onValidationFailure was called
        assertFalse(view.nameValid);
        assertFalse(view.birthdayValid);
        assertFalse(view.phoneNumberValid);
        assertFalse(view.emailValid);

        // User input valid values again
        view.name.setValue("Alice", true);
        view.birthday.setValue(new Date(603255600000L), true);
        view.phoneNumber.setValue("505-98765", true);
        view.email.setValue("alice@alice.org", true);

        // Check if onValidationSuccess was called
        assertTrue(view.nameValid);
        assertTrue(view.birthdayValid);
        assertTrue(view.phoneNumberValid);
        assertTrue(view.emailValid);
    }

    public void testUnbind() {
        // Create the event bus
        final EventBus eventBus = GWT.create(SimpleEventBus.class);

        // Create view
        final PersonViewImpl view = GWT.create(PersonViewImpl.class);

        // Create presenter (and bind)
        final PersonPresenter presenter = new PersonPresenter(eventBus, view);
        presenter.bind();

        // Create model
        final String name = "John Doe";
        final double birthdayTime = 318394800000.0;
        final int phoneNumber = 55512345;
        final String email = "john@doe.com";

        final Person model = new Person();
        model.setName(name);
        model.setBirthday(new Date((long) birthdayTime));
        model.setPhoneNumber(phoneNumber);
        model.setEmail(email);

        // Start editing
        presenter.edit(model);

        // Check values at view
        assertEquals(name, view.name.getValue());
        assertEquals(birthdayTime, (double) view.birthday.getValue().getTime());
        assertEquals(PersonProperties.PHONE_NUMBER_FORMATTER.format(phoneNumber), view.phoneNumber.getValue());
        assertEquals(email, view.email.getValue());

        // Simulate presenter unbinding
        // After that, the binding must not work anymore
        presenter.unbind();

        // Update the model and refresh values to view
        final String name2 = "Alice";
        final double birthdayTime2 = 603255600000.0;
        final Integer phoneNumber2 = 50598765;
        final String email2 = "alice@alice.org";

        presenter.getPerson().setName(name2);
        presenter.getPerson().setBirthday(new Date((long)birthdayTime2));
        presenter.getPerson().setPhoneNumber(phoneNumber2);
        presenter.getPerson().setEmail(email2);

        presenter.refresh();

        // Check *old* values at view
        // I.e. the values were not actually sent to the view through databind
        assertEquals(name, view.name.getValue());
        assertEquals(birthdayTime, (double) view.birthday.getValue().getTime());
        assertEquals(PersonProperties.PHONE_NUMBER_FORMATTER.format(phoneNumber), view.phoneNumber.getValue());
        assertEquals(email, view.email.getValue());

        // User input new values
        view.name.setValue("Bob", true);
        view.birthday.setValue(new Date(), true);
        view.phoneNumber.setValue("545-76765", true);
        view.email.setValue("bob@bob.org", true);

        // Check *old* values at presenter
        assertEquals(name2, presenter.getPerson().getName());
        assertEquals(birthdayTime2, (double) presenter.getPerson().getBirthday().getTime());
        assertEquals(phoneNumber2, presenter.getPerson().getPhoneNumber());
        assertEquals(email2, presenter.getPerson().getEmail());
    }
}
