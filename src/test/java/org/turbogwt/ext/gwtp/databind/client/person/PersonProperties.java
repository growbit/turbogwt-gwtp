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

import java.util.Date;

import javax.annotation.Nullable;

import org.turbogwt.mvp.databind.format.Formatter;
import org.turbogwt.mvp.databind.property.DatePropertyAccessor;
import org.turbogwt.mvp.databind.property.NumberPropertyAccessor;
import org.turbogwt.mvp.databind.property.TextPropertyAccessor;
import org.turbogwt.mvp.databind.validation.EmailValidator;
import org.turbogwt.mvp.databind.validation.RequiredValidator;
import org.turbogwt.mvp.databind.validation.Validation;
import org.turbogwt.mvp.databind.validation.ValidationMessage;
import org.turbogwt.mvp.databind.validation.Validator;

/**
 * Databinding functions of Person.
 *
 * @author Danilo Reinert
 */
public final class PersonProperties {

    /* IDS */

    public static final String NAME = "name";
    public static final String BIRTHDAY = "birthday";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String EMAIL = "email";

    /* ACCESSORS */

    public static final TextPropertyAccessor<Person> NAME_ACCESSOR = new TextPropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable String value) {
            person.setName(value);
        }

        @Nullable
        @Override
        public String getValue(Person person) {
            return person.getName();
        }
    };

    public static final DatePropertyAccessor<Person> BIRTHDAY_ACCESSOR = new DatePropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable Date value) {
            person.setBirthday(value);
        }

        @Nullable
        @Override
        public Date getValue(Person person) {
            return person.getBirthday();
        }
    };

    public static final NumberPropertyAccessor<Person> PHONE_NUMBER_ACCESSOR = new NumberPropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable Number value) {
            person.setPhoneNumber((Integer) value);
        }

        @Nullable
        @Override
        public Number getValue(Person person) {
            return person.getPhoneNumber();
        }
    };

    public static final TextPropertyAccessor<Person> EMAIL_ACCESSOR = new TextPropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable String value) {
            person.setEmail(value);
        }

        @Nullable
        @Override
        public String getValue(Person person) {
            return person.getEmail();
        }
    };

    /* VALIDATORS */

    public static final Validator<Person, String> NAME_VALIDATOR =
            new RequiredValidator<Person, String>("The name is required.");

    public static final Validator<Person, Date> BIRTHDAY_VALIDATOR = new Validator<Person, Date>() {
        @Override
        public Validation validate(Person person, @Nullable Date value) {
            if (value == null) {
                final ValidationMessage message =
                        new ValidationMessage("The birthday date is required.", ValidationMessage.Type.ERROR);
                return Validation.invalid(message);
            }

            if (value.after(new Date())) {
                final ValidationMessage message =
                        new ValidationMessage("The birthday date cannot be after today.", ValidationMessage.Type.ERROR);
                return Validation.invalid(message);
            }

            return Validation.valid();
        }
    };

    public static Validator<Person, Number> PHONE_NUMBER_VALIDATOR =
            new RequiredValidator<Person, Number>("Phone number is required.");

    public static Validator<Person, String> EMAIL_VALIDATOR =
            new EmailValidator<Person>("Invalid email.", true, "The e-mail is required.");

    /* FORMATTERS */

    public static Formatter<Number, String> PHONE_NUMBER_FORMATTER = new Formatter<Number, String>() {
        @Nullable
        @Override
        public Number unformat(@Nullable String formattedValue) {
            if (formattedValue != null && !formattedValue.isEmpty()) {
                // It will transform 999-99999 into 99999999
                final String phoneStr = formattedValue.substring(0, 3) + formattedValue.substring(4);
                return Integer.valueOf(phoneStr);
            }
            return null;
        }

        @Nullable
        @Override
        public String format(@Nullable Number rawValue) {
            if (rawValue != null) {
                // It will display 999-99999
                final String phoneStr = String.valueOf(rawValue);
                return phoneStr.substring(0, 3) + "-" + phoneStr.substring(3);
            }
            return null;
        }
    };
}
