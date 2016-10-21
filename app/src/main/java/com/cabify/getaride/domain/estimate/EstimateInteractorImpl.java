package com.cabify.getaride.domain.estimate;

import android.location.Address;

import com.cabify.getaride.data.entity.request.entity.Stop;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.data.repository.estimate.EstimateRepository;
import com.cabify.getaride.data.repository.estimate.EstimateRepositoryImpl;
import com.cabify.getaride.presentation.internal.di.components.ActivityComponent;
import com.cabify.getaride.presentation.internal.di.components.ApplicationComponent;
import com.cabify.getaride.presentation.utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class EstimateInteractorImpl implements EstimateInteractor, EstimateRepository.OnRequestListener {

    @Inject
    protected EstimateRepository repository;

    private OnEstimationRequestListener listener;

    public EstimateInteractorImpl(OnEstimationRequestListener listener, ActivityComponent activityComponent) {
        this.listener = listener;

        activityComponent.inject(this);
    }

    @Override
    public void getEstimationForStops(String authToken, LatLng fromPoint, Address fromAddress, LatLng toPoint, Address toAddress, Calendar startAtCalendar) {
        List<Stop> stops = new ArrayList<>();
        Stop fromStop = new Stop(fromPoint, fromAddress);
        stops.add(fromStop);

        Stop toStop = new Stop(toPoint, toAddress);
        stops.add(toStop);

        String startAt = (startAtCalendar != null) ? Utils.getDateStringFromDate(startAtCalendar) : "";

        repository.getEstimation(authToken, stops, startAt, this);
    }

    @Override
    public void onSucceed(List<EstimationItem> estimationItemList) {
        if(listener != null) {
            listener.onEstimationRequestSucceed(estimationItemList);
        }
    }

    @Override
    public void onError(String errorMessage) {
        if(listener != null) {
            listener.onEstimationRequestError(errorMessage);
        }
    }

    @Override
    public void onException(Throwable t) {
        if(listener != null) {
            listener.onEstimationRequestException(t);
        }
    }
}
