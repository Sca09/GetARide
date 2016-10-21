package com.cabify.getaride.presentation.view.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cabify.getaride.R;
import com.cabify.getaride.presentation.AndroidApplication;
import com.cabify.getaride.presentation.internal.di.components.ActivityComponent;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.internal.di.components.DaggerActivityComponent;
import com.cabify.getaride.presentation.internal.di.components.DaggerApplicationComponent;
import com.cabify.getaride.presentation.internal.di.modules.ActivityModule;
import com.cabify.getaride.presentation.internal.di.modules.ApplicationModule;
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

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void showSnackbar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     * @param isLastFragmentInFlow Define if the activity container will close when back button is pressed. If 'yes' the activity will close then this fragment is shwon and the back button is pressed
     * @param duplicateFragment Define if the fragment will be duplicated even if the current fragment shown is the same type. If 'false' and current fragment is the same type no new fragment will be created. Note: implemented for the Content Details Fragment to be able to show several fragments in a row.
     */

    protected void addFragment(int containerViewId, Fragment fragment, boolean isLastFragmentInFlow, boolean duplicateFragment) {
        // Clean all the back stack if we are showing a final fragment
        if(isLastFragmentInFlow) {
            this.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        String fragmentTag = fragment.getClass().getSimpleName();
        boolean fragmentPopped = this.getFragmentManager().popBackStackImmediate(fragmentTag, 0);

        if(!fragmentPopped) {
            FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
            // Replace fragment is it is not in the back stack or it is not current fragment
            if(this.getFragmentManager().findFragmentByTag(fragmentTag) == null || duplicateFragment || !isCurrentFragment(fragment)) {
                if(!isLastFragmentInFlow) {
                    fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
                }
                fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
                fragmentTransaction.commit();
            }
        }
    }

    protected boolean isCurrentFragment(Fragment fragment) {
        Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(currentFragment == null) {
            return false;
        }

        return currentFragment.getClass().getName().equals(fragment.getClass().getName());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
