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

public class ProductsRVAdapters extends RecyclerView.Adapter<ProductsRVAdapters.ProductsViewHolder> {

    private Context context;
    private List<ProductModel> productsList;
    private OnItemClickListener onItemClickListener;
    private OnAddProductToCart onAddProductToCart;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnAddProductToCart {
        void onAddClick(View view, int position);
    }

    public ProductsRVAdapters(Context context, List<ProductModel> productsList, OnItemClickListener onItemClickListener, OnAddProductToCart onAddProductToCart) {
        this.context = context;
        this.productsList = productsList;
        this.onItemClickListener = onItemClickListener;
        this.onAddProductToCart = onAddProductToCart;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_rv_item, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, final int position) {

        ProductModel productModel = productsList.get(position);
        Glide.with(context).load(productModel.getProductPhoto()).into(holder.productsIv);
        holder.titleTv.setText(productModel.getTitle());
        holder.detailsTv.setText(productModel.getDetails());
        holder.priceTv.setText(productModel.getPriceFinalText());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddProductToCart.onAddClick(view, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView productsIv;
        TextView detailsTv;
        TextView titleTv;
        TextView priceTv;
        ImageButton addBtn;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            priceTv = itemView.findViewById(R.id.product_price_tv);
            titleTv = itemView.findViewById(R.id.product_title_tv);
            detailsTv = itemView.findViewById(R.id.product_details_tv);
            productsIv = itemView.findViewById(R.id.product_iv);
            addBtn = itemView.findViewById(R.id.add_product_ib);

        }
    }
}
