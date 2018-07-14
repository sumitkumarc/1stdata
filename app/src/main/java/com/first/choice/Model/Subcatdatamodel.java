package com.first.choice.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevalt on 4/3/2018.
 */

public class Subcatdatamodel {

    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_image")
    @Expose
    private String catImage;
    @SerializedName("cat_title")
    @Expose
    private String catTitle;
    @SerializedName("total_design")
    @Expose
    private Integer totalDesign;
    @SerializedName("starting_price")
    @Expose
    private String startingPrice;
    @SerializedName("discount")
    @Expose
    private Integer discount;


    public String getCatTitle() {
        return catTitle;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }
    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public Integer getTotalDesign() {
        return totalDesign;
    }

    public void setTotalDesign(Integer totalDesign) {
        this.totalDesign = totalDesign;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }
}
