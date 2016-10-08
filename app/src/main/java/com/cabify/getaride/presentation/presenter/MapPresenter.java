package com.cabify.getaride.presentation.presenter;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface MapPresenter extends Presenter {

    public void getEstimation(LatLng fromPoint, Address fromAddress, LatLng toPoint, Address toAddress, Calendar startAtCalendar);
}
