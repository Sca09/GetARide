package com.cabify.getaride.presentation.view.fragment;

import android.app.Fragment;

import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.navigation.Navigator;
import com.cabify.getaride.presentation.view.activity.BaseActivity;

/**
 * Created by davidtorralbo on 08/10/16.
 */

public abstract class BaseFragment extends Fragment {

    protected Navigator getNavigator() {
        return ((BaseActivity) getActivity()).getNavigator();
    }

    public ApplicationComponent getApplicationComponent() {
        return ((BaseActivity) getActivity()).getApplicationComponent();
    }
}