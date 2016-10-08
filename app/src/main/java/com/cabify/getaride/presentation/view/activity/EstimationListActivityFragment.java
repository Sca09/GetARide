package com.cabify.getaride.presentation.view.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabify.getaride.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EstimationListActivityFragment extends Fragment {

    public EstimationListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estimation_list, container, false);
    }
}
