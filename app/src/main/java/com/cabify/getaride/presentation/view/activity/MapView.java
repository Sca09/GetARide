package com.cabify.getaride.presentation.view.activity;

import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;

import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface MapView {

    void showProgress();

    void hideProgress();

    void showEstimateResults(List<EstimationItem> estimationItemList);

    void showErrorMessage(String errorMessage);

    void showDefaultErrorMessage();

    ApplicationComponent getApplicationComponentFromApplication();
}
