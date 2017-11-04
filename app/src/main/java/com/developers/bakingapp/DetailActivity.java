package com.developers.bakingapp;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.developers.bakingapp.model.Ingredient;
import com.developers.bakingapp.model.Step;
import com.developers.bakingapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private String stepJson, ingredientJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        stepJson = getIntent().getStringExtra(Constants.KEY_STEPS);
        ingredientJson = getIntent().getStringExtra(Constants.KEY_INGREDIENTS);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_STEPS_JSON, stepJson);
        bundle.putString(Constants.KEY_INGREDIENTS_JSON, ingredientJson);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_fragment, detailFragment).commit();
    }

}
