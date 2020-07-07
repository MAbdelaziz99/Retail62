package com.example.retail62.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retail62.R;
import com.example.retail62.asynctask.InsertProductsAsyncTask;
import com.example.retail62.asynctask.UpdateProductsAsyncTask;
import com.example.retail62.models.ProductModel;
import com.example.retail62.room.RoomFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends Fragment {

    private ImageView productsIv;
    private TextView titleTv;
    private TextView detailsTv;
    private TextView priceTv;
    private TextView descriptionTv;
    private Button addToCartBtn;
    private ImageButton incIb;
    private ImageButton decIb;
    private TextView quantity_tv;
    private List<ProductModel> productsList = new ArrayList<>();

    private ProductModel productModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        productsIv = view.findViewById(R.id.product_details_iv);
        titleTv = view.findViewById(R.id.product_details_title_tv);
        detailsTv = view.findViewById(R.id.product_details_details_tv);
        priceTv = view.findViewById(R.id.product_details_price_tv);
        descriptionTv = view.findViewById(R.id.product_details_description_tv);
        addToCartBtn = view.findViewById(R.id.add_to_cart_btn);
        incIb = view.findViewById(R.id.inc_item_details_ib);
        decIb = view.findViewById(R.id.dec_item_details_ib);
        quantity_tv = view.findViewById(R.id.quantity_details_tv);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            productModel = (ProductModel) args.getSerializable("productModel");

            titleTv.setText(productModel.getTitle());
            detailsTv.setText(productModel.getDetails());
            priceTv.setText(productModel.getPriceFinalText());
            descriptionTv.setText(productModel.getDescription());
            Glide.with(requireContext()).load(productModel.getProductPhoto()).into(productsIv);

            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new InsertProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute(productModel);
                    Navigation.findNavController(view).navigate(R.id.action_productDetailsFragment_to_cartFragment);
                }
            });

        }
    }
}