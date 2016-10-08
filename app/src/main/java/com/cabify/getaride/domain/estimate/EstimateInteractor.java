package com.cabify.getaride.domain.estimate;

import android.location.Address;

import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface EstimateInteractor {

    interface OnEstimationRequestListener {

        void onEstimationRequestSucceed(List<EstimationItem> estimationItemList);

        void onEstimationRequestError(String errorMessage);

        void onEstimationRequestException(Throwable t);

    }

    void getEstimationForStops(String authToken, LatLng fromPoint, Address fromAddress, LatLng toPoint, Address toAddress, Calendar startAtCalendar);

}
