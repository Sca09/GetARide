package com.cabify.getaride.data.entity.response;

import com.cabify.getaride.data.entity.response.entity.EstimationItem;
import com.cabify.getaride.data.entity.response.entity.Eta;
import com.cabify.getaride.data.entity.response.entity.VehicleType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class EstimateResponse {

    private List<EstimationItem> estimationItemList;

    public List<EstimationItem> getEstimationItemList() {
        return estimationItemList;
    }

    public void setEstimationItemList(List<EstimationItem> estimationItemList) {
        this.estimationItemList = estimationItemList;
    }
}
