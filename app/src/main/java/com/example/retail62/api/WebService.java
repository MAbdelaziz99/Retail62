package com.example.retail62.api;

import com.example.retail62.models.CategoriesResponse;
import com.example.retail62.models.ProductsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface WebService {

    @GET("products")
    Single<ProductsResponse> getProducts();

    @GET("categories")
    Single<CategoriesResponse> getCategories();
}
