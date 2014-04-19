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

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

/**
 * The interface for view classes that handles all the UI-related code for a
 * {@link com.gwtplatform.mvp.client.Presenter} supporting Databind.
 *
 * @param <H> your {@link DatabindUiHandlers} implementation. (usually the presenter)
 * @author Danilo Reinert
 */
public interface DatabindView<H extends DatabindUiHandlers> extends View, HasUiHandlers<H>,
        org.turbogwt.mvp.databind.DatabindView {
}
