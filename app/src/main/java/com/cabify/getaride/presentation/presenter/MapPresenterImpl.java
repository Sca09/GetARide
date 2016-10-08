package com.cabify.getaride.presentation.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.domain.estimate.EstimateInteractor;
import com.cabify.getaride.domain.estimate.EstimateInteractorImpl;
import com.cabify.getaride.presentation.utils.Utils;
import com.cabify.getaride.presentation.view.activity.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class MapPresenterImpl implements MapPresenter, EstimateInteractor.OnEstimationRequestListener {

    @Inject
    protected
    Context context;

    private MapView view;
    private EstimateInteractor estimateInteractor;

    private Marker fromMarker;
    private LatLng fromLatLng;
    private Address fromAddress;
    private Marker toMarker;
    private LatLng toLatLng;
    private Address toAddress;
    private Calendar startAtCalendar;
    private boolean startAtDatSet = false;
    private  boolean isFabOpen = false;

    public MapPresenterImpl(MapView view) {
        this.view = view;

        if(this.view != null) {
            view.getApplicationComponentFromApplication().inject(this);
        }

        estimateInteractor = new EstimateInteractorImpl(this);
    }

    @Override
    public void getEstimation(LatLng fromPoint, Address fromAddress, LatLng toPoint, Address toAddress, Calendar startAtCalendar) {
        if(view != null) {
            view.showProgress();

            estimateInteractor.getEstimationForStops(context.getString(R.string.cabify_auth_token), fromPoint, fromAddress, toPoint, toAddress, startAtCalendar);
        }
    }

    @Override
    public void buttonClickedFabCurrentLocation() {
        if(isFabOpen){
            view.closeFab();
            closeEstimationLayout();
        } else {
            view.openFab();
            view.focusOnCurrentPosition();
            view.setCurrentLocation();
        }
    }

    @Override
    public void buttonClickedFabGetEstimation() {
        getEstimation(fromLatLng, fromAddress, toLatLng, toAddress, (startAtDatSet) ? startAtCalendar : null);
    }

    @Override
    public void buttonClickedRemoveFromLocation() {
        closeEstimationLayout();
        if(isFabOpen){
            view.closeFab();
        }
    }

    @Override
    public void buttonClickedRemoveToLocation() {
        view.closeToLayout();
    }

    @Override
    public void buttonClickedRemoveStartAt() {
        if(startAtDatSet) {
            view.resetStartAtDate();
        } else {
            view.initDatePicker();
        }
    }

    @Override
    public void closeEstimationLayout() {
        view.closeFromLayout();
        view.closeToLayout();
        view.resetStartAtDate();
        view.clearMap();
    }

    @Override
    public void closeFromLayout() {
        if(fromMarker != null) {
            fromMarker.remove();
        }
        fromLatLng = null;
    }

    @Override
    public void closeToLayout() {
        if(toMarker != null) {
            toMarker.remove();
        }
        toLatLng = null;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void onEstimationRequestSucceed(List<EstimationItem> estimationItemList) {
        if(view != null) {
            view.showEstimateResults(estimationItemList);
            view.hideProgress();
        }
    }

    @Override
    public void onEstimationRequestError(String errorMessage) {
        if(view != null) {
            view.showErrorMessage(errorMessage);
            view.hideProgress();
        }
    }

    @Override
    public void onEstimationRequestException(Throwable t) {
        if(view != null) {
            view.showDefaultErrorMessage();
            view.hideProgress();
        }
    }

    @Override
    public void setPoint(LatLng point) {
        Address address = null;
        try {
            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(point.latitude, point.longitude, 1);
            if (addresses.isEmpty()) {
                Log.d("GetARide", "Waiting for Location");
            }
            else if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        if(fromLatLng == null) {
            fromLatLng = point;
            fromMarker = view.addMarker(point, context.getString(R.string.from));
            if(address != null) {
                fromAddress = address;
                view.setFromEditText(context.getString(R.string.address_in_box, address.getAddressLine(0), address.getAddressLine(1)));
            } else {
                view.setFromEditText(point.toString());
            }

            view.openFromLayout();
            view.focusOnPoint(point.latitude, point.longitude);
        } else if(toLatLng == null){
            toLatLng = point;
            toMarker = view.addMarker(point, context.getString(R.string.to));
            if(address != null) {
                toAddress = address;
                view.setToEditText(context.getString(R.string.address_in_box, address.getAddressLine(0), address.getAddressLine(1)));
            } else {
                view.setToEditText(point.toString());
            }

            view.openToLayout();

            if(!isFabOpen) {
                view.openFab();
            }
        }
    }

    @Override
    public void setFabOpenStatus(boolean status) {
        isFabOpen = status;
    }

    @Override
    public boolean isFabOpen() {
        return isFabOpen;
    }

    @Override
    public boolean isStartAtDatSet() {
        return startAtDatSet;
    }

    @Override
    public void setStartAtDatSet(boolean startAtDatSet) {
        this.startAtDatSet = startAtDatSet;
    }

    @Override
    public void setStartAtDate(int year, int monthOfYear, int dayOfMonth) {
        startAtCalendar = Calendar.getInstance();
        startAtCalendar.set(Calendar.YEAR, year);
        startAtCalendar.set(Calendar.MONTH, monthOfYear);
        startAtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        view.initTimePicker();
    }

    @Override
    public void setStartAtTime(int hourOfDay, int minute) {
        startAtCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startAtCalendar.set(Calendar.MINUTE, minute);

        setStartAtDatSet(true);
        view.setStartAtTxt(Utils.getDateStringFromDate(startAtCalendar));
    }
}
