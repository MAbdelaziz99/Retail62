package com.example.retail62.asynctask;

import android.os.AsyncTask;

import com.example.retail62.models.ProductModel;
import com.example.retail62.room.DAO;

public class UpdateProductsAsyncTask extends AsyncTask<ProductModel, Void, Void> {

    private DAO dao;

    public UpdateProductsAsyncTask(DAO dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {
        dao.updateProducts(productModels[0]);
        return null;
    }
}
