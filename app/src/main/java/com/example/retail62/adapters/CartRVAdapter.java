package com.example.retail62.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retail62.R;
import com.example.retail62.models.ProductModel;

import java.util.List;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.CartViewHolder> {

    private Context context;
    private List<ProductModel> productsList;
    private OnIncrementalProducts onIncrementalProducts;
    private OnDecrementalProducts onDecrementalProducts;

    public interface OnIncrementalProducts {
        void onIncItem(View view, int position);
    }

    public interface OnDecrementalProducts {
        void onDecItem(View view, int position);
    }

    public CartRVAdapter(Context context, List<ProductModel> productsList, OnIncrementalProducts onIncrementalProducts, OnDecrementalProducts onDecrementalProducts) {
        this.context = context;
        this.productsList = productsList;
        this.onIncrementalProducts = onIncrementalProducts;
        this.onDecrementalProducts = onDecrementalProducts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_rv_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        ProductModel productModel = productsList.get(position);
        Glide.with(context).load(productModel.getProductPhoto()).into(holder.cartIv);
        holder.titleTv.setText(productModel.getTitle());
        holder.detailsTv.setText(productModel.getDetails());
        holder.priceTv.setText(productModel.getPriceFinalText());
        holder.quantityTv.setText(productModel.getProductsQuantity() + "");
        holder.incIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncrementalProducts.onIncItem(view, position);
            }
        });

        holder.decIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecrementalProducts.onDecItem(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView cartIv;
        TextView titleTv;
        TextView detailsTv;
        TextView priceTv;
        ImageButton incIb;
        ImageButton decIb;
        TextView quantityTv;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartIv = itemView.findViewById(R.id.cart_iv);
            titleTv = itemView.findViewById(R.id.cart_title_tv);
            detailsTv = itemView.findViewById(R.id.cart_details_tv);
            priceTv = itemView.findViewById(R.id.cart_price_tv);
            incIb = itemView.findViewById(R.id.inc_item_ib);
            decIb = itemView.findViewById(R.id.dec_item_ib);
            quantityTv = itemView.findViewById(R.id.quantity_details_tv);
        }
    }
}
