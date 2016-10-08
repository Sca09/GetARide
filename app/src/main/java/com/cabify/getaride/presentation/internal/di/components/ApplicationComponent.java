/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cabify.getaride.presentation.internal.di.components;

import android.content.Context;

import com.cabify.getaride.presentation.internal.di.modules.ApplicationModule;
import com.cabify.getaride.presentation.presenter.MapPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by davidtorralbo on 07/10/16.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MapPresenterImpl presenter);

    //Exposed to sub-graphs.
    Context context();
}
