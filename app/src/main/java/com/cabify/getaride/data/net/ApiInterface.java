package com.cabify.getaride.data.net;

import com.cabify.getaride.data.entity.request.EstimateRequest;
import com.cabify.getaride.data.entity.response.EstimateResponse;
import com.cabify.getaride.data.entity.response.entity.EstimationItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public interface ApiInterface {

    @POST("estimate")
    Call<List<EstimationItem>> getEstimation(@Header("Authorization") String authToken, @Body EstimateRequest request);

}
