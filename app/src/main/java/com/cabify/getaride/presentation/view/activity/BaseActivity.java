package com.cabify.getaride.presentation.view.activity;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cabify.getaride.R;
import com.cabify.getaride.presentation.AndroidApplication;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.navigation.Navigator;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected void showSnackbar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }
}
