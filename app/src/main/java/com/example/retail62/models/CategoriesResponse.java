package com.example.retail62.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {

    @SerializedName("categories")
    private List<CategoriesModel> categoriesList;

    public List<CategoriesModel> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<CategoriesModel> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
