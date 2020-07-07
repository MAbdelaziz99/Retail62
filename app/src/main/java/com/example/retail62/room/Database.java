package com.example.retail62.room;

import androidx.room.RoomDatabase;

import com.example.retail62.models.ProductModel;

@androidx.room.Database(entities = {ProductModel.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract DAO getProductDao();
}
