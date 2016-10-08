package com.cabify.getaride.presentation.presenter;

import android.content.Context;
import android.location.Address;

import com.cabify.getaride.R;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.domain.estimate.EstimateInteractor;
import com.cabify.getaride.domain.estimate.EstimateInteractorImpl;
import com.cabify.getaride.presentation.view.activity.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

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
}
