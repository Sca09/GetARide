package com.cabify.getaride.presentation.presenter;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface MapPresenter extends Presenter {

    void setPoint(LatLng point);

    void setFabOpenStatus(boolean status);

    boolean isFabOpen();

    boolean isStartAtDatSet();

    void setStartAtDatSet(boolean startAtDatSet);

    void setStartAtDate(int year, int monthOfYear, int dayOfMonth);

    void setStartAtTime(int hourOfDay, int minute);

    void getEstimation(LatLng fromPoint, Address fromAddress, LatLng toPoint, Address toAddress, Calendar startAtCalendar);

    void buttonClickedFabCurrentLocation();

    void buttonClickedFabGetEstimation();

    void buttonClickedRemoveFromLocation();

    void buttonClickedRemoveToLocation();

    void buttonClickedSetStartAt();

    void buttonClickedRemoveStartAt();

    void closeEstimationLayout();

    void closeFromLayout();

    void closeToLayout();
}
