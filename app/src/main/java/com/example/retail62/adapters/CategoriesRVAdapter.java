package com.example.retail62.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retail62.R;
import com.example.retail62.models.CategoriesModel;
import com.example.retail62.models.ProductModel;

import java.util.List;

public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.CategoriesViewHolder> {

    private Context context;
    private List<CategoriesModel> categoriesList;

    public CategoriesRVAdapter(Context context, List<CategoriesModel> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        CategoriesModel categoriesModel = categoriesList.get(position);

        holder.titleTv.setText(categoriesModel.getCategoriesTitle());
        Glide.with(context).load(categoriesModel.getCategoriesPhoto()).into(holder.categoryAvatar);

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        ImageView categoryAvatar;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.category_tv);
            categoryAvatar = itemView.findViewById(R.id.category_iv);

        }
    }
}
