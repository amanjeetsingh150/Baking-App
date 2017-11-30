package com.developers.bakingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developers.bakingapp.activities.MainActivity;
import com.developers.bakingapp.adapters.RecipeAdapter;
import com.developers.bakingapp.model.Result;
import com.developers.bakingapp.util.ApiInterface;
import com.developers.bakingapp.util.Constants;
import com.developers.bakingapp.util.SimpleIdlingResource;
import com.developers.coolprogressviews.DoubleArcProgress;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private static final String TAG = MainFragment.class.getSimpleName();
    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;
    String resultJson;
    Gson gson;
    SimpleIdlingResource idlingResource;
    @BindView(R.id.double_progress_arc)
    DoubleArcProgress doubleArcProgress;
    private ApiInterface apiInterface;
    private List<Result> resultList;
    private boolean mTwoPane;
    private RecipeAdapter recipeAdapter;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        apiInterface = Constants.getRetrofit().create(ApiInterface.class);
        idlingResource = (SimpleIdlingResource) ((MainActivity) getActivity()).getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        doubleArcProgress.setVisibility(View.VISIBLE);
        if (isNetworkConnected()) {
            resultList = getRecipeList();
        } else {
            Snackbar.make(view, getActivity().getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        }
        gson = new Gson();
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    public List<Result> getRecipeList() {
        apiInterface.getDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Result>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<Result> value) {
                        resultList = value;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage() + " ");
                        showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        resultJson = gson.toJson(resultList);
                        recipeAdapter = new RecipeAdapter(getActivity(), resultList);
                        mTwoPane = MainActivity.getNoPane();
                        if (mTwoPane) {
                            //GridLayout
                            GridLayoutManager gridLayoutManager = new
                                    GridLayoutManager(getActivity(), 3);
                            recipeRecyclerView.setLayoutManager(gridLayoutManager);
                            recipeRecyclerView.setAdapter(recipeAdapter);
                            idlingResource.setIdleState(true);
                        } else {
                            //LinearVerticalLayout
                            LinearLayoutManager linearLayoutManager = new
                                    LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recipeRecyclerView.setLayoutManager(linearLayoutManager);
                            recipeRecyclerView.setAdapter(recipeAdapter);
                            idlingResource.setIdleState(true);
                        }
                        doubleArcProgress.setVisibility(View.GONE);
                    }
                });
        return resultList;
    }


    private void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

}
