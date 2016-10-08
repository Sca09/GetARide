package com.cabify.getaride.data.entity.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class Icons implements Serializable {

    @SerializedName("regular")
    @Expose
    private String regular;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}
