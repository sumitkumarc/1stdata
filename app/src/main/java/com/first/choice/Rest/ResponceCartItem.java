package com.first.choice.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.first.choice.Model.CartList;
import com.first.choice.Model.Subcatdatamodel;

import java.util.List;

/**
 * Created by kevalt on 4/13/2018.
 */

public class ResponceCartItem {
    @SerializedName("Success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod_charge")
    @Expose
    private String codCharge;
    @SerializedName("data")
    @Expose
    private List<CartList> data = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CartList> getData() {
        return data;
    }

    public void setData(List<CartList> data) {
        this.data = data;
    }
    public String getCodCharge() {
        return codCharge;
    }

    public void setCodCharge(String codCharge) {
        this.codCharge = codCharge;
    }
}
