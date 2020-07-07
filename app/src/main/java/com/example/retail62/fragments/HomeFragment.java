package com.example.retail62.fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retail62.R;
import com.example.retail62.adapters.ProductsRVAdapters;
import com.example.retail62.api.RetrofitFactory;
import com.example.retail62.api.WebService;
import com.example.retail62.asynctask.InsertProductsAsyncTask;
import com.example.retail62.models.ProductModel;
import com.example.retail62.models.ProductsResponse;
import com.example.retail62.room.RoomFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private RecyclerView productsRv;
    private List<ProductModel> productsList = new ArrayList<>();
    private ProductsRVAdapters productsAdapter;
    private ProgressDialog dialog;
    private SingleObserver<ProductsResponse> productsObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WebService webService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        productsRv = view.findViewById(R.id.products_rv);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpProgressDialog();
        setUpRecyclerView();
        setUpProductsObserver();
        doProductsSubscription();
    }

    private void doProductsSubscription() {
        webService = RetrofitFactory.getRetrofit().create(WebService.class);
        Single<ProductsResponse> getProducts = webService.getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        getProducts.subscribe(productsObserver);
    }

    private void setUpProductsObserver() {
        productsObserver = new SingleObserver<ProductsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ProductsResponse productsResponse) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                productsList.clear();
                productsList.addAll(productsResponse.getProductsList());
                productsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        productsRv.setLayoutManager(layoutManager);
        productsRv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(12), true));
        productsAdapter = new ProductsRVAdapters(requireContext(), productsList, new ProductsRVAdapters.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                ProductModel productModel = productsList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("productModel", productModel);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);
            }
        }

        , new ProductsRVAdapters.OnAddProductToCart() {
            @Override
            public void onAddClick(View view, int position) {
                ProductModel productModel = productsList.get(position);
                new InsertProductsAsyncTask(RoomFactory.getRoomObject(requireContext()).getProductDao()).execute(productModel);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cartFragment);
            }
        });
        productsRv.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

    }

    private void setUpProgressDialog() {
        dialog = new ProgressDialog(requireContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}