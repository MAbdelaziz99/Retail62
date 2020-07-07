package com.example.retail62.asynctask;

import android.os.AsyncTask;

import androidx.room.Dao;

import com.example.retail62.room.DAO;

public class DeleteProductsAsyncTask extends AsyncTask<Void, Void, Void> {

    private DAO dao;

    public DeleteProductsAsyncTask(DAO dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deleteAllProducts();
        return null;
    }
}
