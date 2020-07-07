package com.example.retail62.models;

import com.google.gson.annotations.SerializedName;

public class CategoriesModel {

    @SerializedName("id")
    private int categoriesId;

    @SerializedName("name")
    private String categoriesTitle;

    @SerializedName("avatar")
    private String categoriesPhoto;

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoriesTitle() {
        return categoriesTitle;
    }

    public void setCategoriesTitle(String categoriesTitle) {
        this.categoriesTitle = categoriesTitle;
    }

    public String getCategoriesPhoto() {
        return categoriesPhoto;
    }

    public void setCategoriesPhoto(String categoriesPhoto) {
        this.categoriesPhoto = categoriesPhoto;
    }
}
