package com.example.retail62.asynctask;

import android.os.AsyncTask;

import com.example.retail62.models.ProductModel;
import com.example.retail62.room.DAO;

import java.util.List;

public class GetProductsAsyncTask extends AsyncTask<Void, Void, List<ProductModel>> {

    private DAO dao;

    public GetProductsAsyncTask(DAO dao) {
        this.dao = dao;
    }

    @Override
    protected List<ProductModel> doInBackground(Void... voids) {
        return dao.getAllProducts();
    }
}
