package com.cabify.getaride.presentation.view.activity;


import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cabify.getaride.R;
import com.cabify.getaride.presentation.AndroidApplication;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected void showSnackbar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

}
