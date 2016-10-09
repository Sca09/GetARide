package com.cabify.getaride.presentation.view;

import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface MapView {

    void resetViews();

    void showProgress();

    void hideProgress();

    Marker addMarker(LatLng point, String title);

    void focusOnPoint(double latitude, double longitude);

    void focusOnCurrentPosition();

    void setCurrentLocation();

    void openFab();

    void closeFab();

    void setFromEditText(String text);

    void setToEditText(String text);

    void setStartAtTxt(String text);

    void openFromLayout();

    void closeFromLayout();

    void openToLayout();

    void closeToLayout();

    void clearMap();

    void resetStartAtDate();

    void initDatePicker();

    void initTimePicker();

    void showEstimateResults(List<EstimationItem> estimationItemList);

    void showErrorMessage(String errorMessage);

    void showDefaultErrorMessage();

    ApplicationComponent getApplicationComponentFromApplication();
}
