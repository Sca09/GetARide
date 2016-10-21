package com.cabify.getaride.presentation.internal.di.modules;

import android.app.Activity;

import com.cabify.getaride.data.net.ApiClient;
import com.cabify.getaride.data.net.ApiInterface;
import com.cabify.getaride.data.repository.estimate.EstimateRepository;
import com.cabify.getaride.data.repository.estimate.EstimateRepositoryImpl;
import com.cabify.getaride.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
    this.activity = activity;
  }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }


    @Provides
    @PerActivity
    EstimateRepository provideEstimateRepository() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return new EstimateRepositoryImpl(apiService);
    }
}
