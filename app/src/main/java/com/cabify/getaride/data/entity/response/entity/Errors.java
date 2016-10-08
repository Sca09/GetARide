package com.cabify.getaride.data.entity.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class Errors {

    @SerializedName("some_attribute")
    @Expose
    private List<String> someAttribute = new ArrayList<String>();
    @SerializedName("some_other")
    @Expose
    private List<String> someOther = new ArrayList<String>();

    public List<String> getSomeAttribute() {
        return someAttribute;
    }

    public void setSomeAttribute(List<String> someAttribute) {
        this.someAttribute = someAttribute;
    }

    public List<String> getSomeOther() {
        return someOther;
    }

    public void setSomeOther(List<String> someOther) {
        this.someOther = someOther;
    }
}
