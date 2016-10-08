package com.cabify.getaride.data.entity.request.entity;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class Stop {

    @SerializedName("loc")
    @Expose
    private List<Double> loc = new ArrayList<Double>();

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("addr")
    @Expose
    private String address;

    @SerializedName("num")
    @Expose
    private String number;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("country")
    @Expose
    private String country;

    public Stop() {

    }

    public Stop(LatLng point, Address address) {
        if(point != null) {
            List<Double> fromLoc = new ArrayList<Double>();
            fromLoc.add(point.latitude);
            fromLoc.add(point.longitude);
            setLoc(fromLoc);
        }
    }

    public List<Double> getLoc() {
        return loc;
    }

    public void setLoc(List<Double> loc) {
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
