package com.developers.bakingapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developers.bakingapp.model.Ingredient;
import com.developers.bakingapp.model.Step;
import com.developers.bakingapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private String steps, ingredients;
    private Gson gson;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;

    public DetailFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();
        steps = bundle.getString(Constants.KEY_STEPS_JSON);
        ingredients = bundle.getString(Constants.KEY_INGREDIENTS_JSON);
        gson=new Gson();
        ingredientList = gson.fromJson(ingredients,
                new TypeToken<List<Ingredient>>() {
                }.getType());
        stepList = gson.fromJson(steps,
                new TypeToken<List<Step>>() {
                }.getType());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
