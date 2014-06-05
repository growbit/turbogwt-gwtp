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

package org.turbogwt.ext.gwtp.databind.test.person;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import javax.annotation.Nullable;

import org.turbogwt.ext.gwtp.databind.client.DatabindViewImpl;
import org.turbogwt.mvp.databind.client.Strategy;
import org.turbogwt.mvp.databind.client.validation.ValidationMessage;

/**
 * Implementation of {@link PersonPresenter.PersonView}.
 *
 * @author Danilo Reinert
 */
public class PersonViewImpl extends DatabindViewImpl<PersonUiHandlers> implements PersonPresenter.PersonView {

    final TextBox name = new TextBox();
    final DateBox birthday = new DateBox();
    final TextBox phoneNumber = new TextBox();
    final TextBox email = new TextBox();

    boolean nameValid, birthdayValid, phoneNumberValid, emailValid = true;

    public PersonViewImpl() {
        // Construct the View (you can do it via UiBinder as well)
        FlowPanel panel = new FlowPanel();
        panel.add(name);
        panel.add(birthday);
        panel.add(phoneNumber);
        panel.add(email);
        initWidget(panel);

        // Bind the widgtes
        bind(PersonProperties.NAME, name, Strategy.ON_CHANGE);
        bind(PersonProperties.BIRTHDAY, birthday, Strategy.ON_CHANGE);
        bind(PersonProperties.PHONE_NUMBER, phoneNumber, Strategy.ON_CHANGE);
        bind(PersonProperties.EMAIL, email, Strategy.ON_CHANGE);
    }

    @Override
    public void onValidationFailure(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation failures.
        // Usually you will notify the user about the failure and how to fix it.

        switch (property) {
            case PersonProperties.NAME:
                nameValid = false;
                break;
            case PersonProperties.BIRTHDAY:
                birthdayValid = false;
                break;
            case PersonProperties.PHONE_NUMBER:
                phoneNumberValid = false;
                break;
            case PersonProperties.EMAIL:
                emailValid = false;
                break;
            default:
        }
    }

    @Override
    public void onValidationSuccess(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation success.
        // Usually you will clean any error message on display.

        switch (property) {
            case PersonProperties.NAME:
                nameValid = true;
                break;
            case PersonProperties.BIRTHDAY:
                birthdayValid = true;
                break;
            case PersonProperties.PHONE_NUMBER:
                phoneNumberValid = true;
                break;
            case PersonProperties.EMAIL:
                emailValid = true;
                break;
            default:
        }
    }
}
