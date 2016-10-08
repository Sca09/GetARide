package com.cabify.getaride.data.repository.estimate;

import com.cabify.getaride.data.entity.request.entity.Stop;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;

import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface EstimateRepository {

    interface OnRequestListener {

        void onSucceed(List<EstimationItem> estimationItemList);

        void onError(String errorMessage);

        void onException(Throwable t);

    }

    void getEstimation(String authToken, List<Stop> stops, String startAt, OnRequestListener listener);
}
