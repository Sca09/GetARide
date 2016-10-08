package com.cabify.getaride.data.entity.request;

import com.cabify.getaride.data.entity.request.entity.Stop;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class EstimateRequest {

    @SerializedName("stops")
    @Expose
    private List<Stop> stops = new ArrayList<Stop>();
    @SerializedName("start_at")
    @Expose
    private String startAt;

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
