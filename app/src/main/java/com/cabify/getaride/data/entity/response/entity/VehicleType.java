package com.cabify.getaride.data.entity.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by davidtorralbo on 07/10/16.
 */

public class VehicleType implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icons")
    @Expose
    private Icons icons;
    @SerializedName("reserved_only")
    @Expose
    private Boolean reservedOnly;
    @SerializedName("asap_only")
    @Expose
    private Boolean asapOnly;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icons getIcons() {
        return icons;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public Boolean getReservedOnly() {
        return reservedOnly;
    }

    public void setReservedOnly(Boolean reservedOnly) {
        this.reservedOnly = reservedOnly;
    }

    public Boolean getAsapOnly() {
        return asapOnly;
    }

    public void setAsapOnly(Boolean asapOnly) {
        this.asapOnly = asapOnly;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
