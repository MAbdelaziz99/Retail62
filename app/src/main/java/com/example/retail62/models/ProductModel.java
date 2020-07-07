package com.example.retail62.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "products")
public class ProductModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private long incrementalId;

    @SerializedName("id")
    private int productId;

    @SerializedName("name")
    @ColumnInfo(name = "details")
    private String details;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("category_id")
    private int productsCategoryId;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("discount")
    private int discount;
    @SerializedName("discount_type")
    private String discountType;
    @SerializedName("currency")
    private String currency;
    @SerializedName("in_stock")
    private int inStock;

    @SerializedName("avatar")
    @ColumnInfo(name = "photo")
    private String productPhoto;

    @SerializedName("price_final")
    private double priceFinal;

    @SerializedName("price_final_text")
    @ColumnInfo(name = "price_final_text")
    private String priceFinalText;

    private int productsQuantity;

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProductsCategoryId() {
        return productsCategoryId;
    }

    public void setProductsCategoryId(int productsCategoryId) {
        this.productsCategoryId = productsCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(double priceFinal) {
        this.priceFinal = priceFinal;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }



    public String getPriceFinalText() {
        return priceFinalText;
    }

    public void setPriceFinalText(String priceFinalText) {
        this.priceFinalText = priceFinalText;
    }

    public long getIncrementalId() {
        return incrementalId;
    }

    public void setIncrementalId(long incrementalId) {
        this.incrementalId = incrementalId;
    }
}
