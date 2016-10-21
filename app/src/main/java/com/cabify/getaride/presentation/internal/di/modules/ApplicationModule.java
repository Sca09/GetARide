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
package com.cabify.getaride.presentation.internal.di.modules;

import android.content.Context;

import com.cabify.getaride.data.net.ApiClient;
import com.cabify.getaride.data.net.ApiInterface;
import com.cabify.getaride.data.repository.estimate.EstimateRepository;
import com.cabify.getaride.data.repository.estimate.EstimateRepositoryImpl;
import com.cabify.getaride.presentation.AndroidApplication;
import com.cabify.getaride.presentation.internal.di.PerActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by davidtorralbo on 07/10/16.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;
    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
    return this.application;
  }
}
