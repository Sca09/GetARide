package com.cabify.getaride.data.repository.estimate;

import com.cabify.getaride.data.entity.request.EstimateRequest;
import com.cabify.getaride.data.entity.request.entity.Stop;
import com.cabify.getaride.data.entity.response.ErrorResponse;
import com.cabify.getaride.data.entity.response.EstimateResponse;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.data.entity.response.utils.ErrorConversor;
import com.cabify.getaride.data.net.ApiClient;
import com.cabify.getaride.data.net.ApiInterface;
import com.cabify.getaride.presentation.internal.di.PerActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidtorralbo on 07/10/16.
 */

@PerActivity
public class EstimateRepositoryImpl implements EstimateRepository, Callback<List<EstimationItem>> {

    private ApiInterface apiService;
    private OnRequestListener listener;

    @Inject
    public EstimateRepositoryImpl(ApiInterface apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getEstimation(String authToken, List<Stop> stops, String startAt, OnRequestListener listener) {
        this.listener = listener;

        EstimateRequest request = new EstimateRequest();
        request.setStops(stops);
        request.setStartAt(startAt);

        Call<List<EstimationItem>> call = apiService.getEstimation(authToken, request);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<EstimationItem>> call, Response<List<EstimationItem>> response) {
        if(listener != null) {
            if(response.isSuccessful()) {
                listener.onSucceed(response.body());
            } else if (response.errorBody() != null ) {
                try {
                    ErrorResponse error = ErrorConversor.parseError(ApiClient.getClient(), response);
                    if (error != null) {
                        listener.onError(error.getMessage());
                    }
                } catch (Exception e) {
                    listener.onException(e);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<List<EstimationItem>> call, Throwable t) {
        if(listener != null) {
            listener.onException(t);
        }
    }
}
