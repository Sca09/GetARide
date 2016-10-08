package com.cabify.getaride.presentation;

import android.app.Application;
import android.content.Intent;

import com.cabify.getaride.BuildTypeConfiguration;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.internal.di.components.DaggerApplicationComponent;
import com.cabify.getaride.presentation.internal.di.modules.ApplicationModule;
import com.cabify.getaride.presentation.view.activity.MainActivity;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();

        if(BuildTypeConfiguration.registerExceptionHandler) {
            this.registerExceptionHandler();
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable throwable) {
                throwable.printStackTrace();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
                startActivity(intent);

                //This will stop your application and take out from it.
                System.exit(1);
            }
        });
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
