package com.example.retail62.asynctask;

import android.os.AsyncTask;

import com.example.retail62.models.ProductModel;
import com.example.retail62.room.DAO;

public class InsertProductsAsyncTask extends AsyncTask<ProductModel, Void, Void> {

    private DAO dao;

    public InsertProductsAsyncTask(DAO dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {
        dao.insertProducts(productModels[0]);
        return null;
    }
}
