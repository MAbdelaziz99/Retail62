package com.example.retail62.fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retail62.R;
import com.example.retail62.adapters.CategoriesRVAdapter;
import com.example.retail62.api.RetrofitFactory;
import com.example.retail62.api.WebService;
import com.example.retail62.models.CategoriesModel;
import com.example.retail62.models.CategoriesResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesFragment extends Fragment {

    private RecyclerView categoriesRv;
    private CategoriesRVAdapter categoriesAdapter;
    private List<CategoriesModel> categoriesList = new ArrayList<>();
    private SingleObserver<CategoriesResponse> categoriesObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressDialog dialog;
    private WebService webService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        categoriesRv = view.findViewById(R.id.categories_rv);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setUpProgressDialog();
        setUpRecyclerView();
        setUpCategoriesObserver();
        doCategoriesSubscription();

    }

    private void doCategoriesSubscription() {
        webService = RetrofitFactory.getRetrofit().create(WebService.class);
        Single<CategoriesResponse> getCategories = webService.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        getCategories.subscribe(categoriesObserver);

    }

    private void setUpProgressDialog() {
        
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    private void setUpCategoriesObserver() {

        categoriesObserver = new SingleObserver<CategoriesResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(CategoriesResponse categoriesResponse) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                categoriesList.clear();
                categoriesList.addAll(categoriesResponse.getCategoriesList());
                categoriesAdapter.notifyDataSetChanged();
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
        categoriesRv.setLayoutManager(layoutManager);
        categoriesRv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(12), true));
        categoriesAdapter = new CategoriesRVAdapter(requireContext(), categoriesList);
        categoriesRv.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();
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