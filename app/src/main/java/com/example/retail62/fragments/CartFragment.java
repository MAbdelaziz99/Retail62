package com.example.retail62.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.retail62.R;
import com.example.retail62.adapters.CartRVAdapter;
import com.example.retail62.asynctask.DeleteProductsAsyncTask;
import com.example.retail62.asynctask.GetProductsAsyncTask;
import com.example.retail62.asynctask.UpdateProductsAsyncTask;
import com.example.retail62.models.ProductModel;
import com.example.retail62.room.RoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CartFragment extends Fragment {

    private RecyclerView cartRv;
    private ArrayList<ProductModel> productsList = new ArrayList<>();
    private CartRVAdapter cartAdapter;
    private Button clearBtn;
    private Button proceedToCheckOutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRv = view.findViewById(R.id.cart_rv);
        clearBtn = view.findViewById(R.id.cart_clear_btn);
        proceedToCheckOutBtn = view.findViewById(R.id.cart_proceed_to_checkout_btn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProducts();
        setUpRecyclerView();
        deleteAllProducts();
    }

    private void deleteAllProducts() {

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute();
                productsList.clear();
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getProducts() {
        productsList.clear();
        try {
            productsList.addAll(new GetProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        cartRv.setLayoutManager(layoutManager);
        cartRv.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        cartAdapter = new CartRVAdapter(requireContext(), productsList, new CartRVAdapter.OnIncrementalProducts() {
            @Override
            public void onIncItem(View view, int position) {
                productsList.get(position).setProductsQuantity(productsList.get(position).getProductsQuantity() + 1);
                new UpdateProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute(productsList.get(position));
                cartAdapter.notifyDataSetChanged();
            }
        }, new CartRVAdapter.OnDecrementalProducts() {
            @Override
            public void onDecItem(View view, int position) {
                productsList.get(position).setProductsQuantity(productsList.get(position).getProductsQuantity() - 1);
                new UpdateProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute(productsList.get(position));
                cartAdapter.notifyDataSetChanged();
            }
        });

        cartRv.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }
}