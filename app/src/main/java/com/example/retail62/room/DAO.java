package com.example.retail62.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retail62.models.ProductModel;

import java.util.List;

@Dao
public interface DAO {

    @Query("SELECT * From products")
    List<ProductModel> getAllProducts();

    @Insert
    void insertProducts(ProductModel productModel);

    @Update
    void updateProducts(ProductModel productModel);

    @Query("DELETE FROM products")
    void deleteAllProducts();
}
