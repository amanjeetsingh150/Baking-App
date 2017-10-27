package com.developers.bakingapp;

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
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        stepJson = getIntent().getStringExtra(Constants.KEY_STEPS);
        ingredientJson = getIntent().getStringExtra(Constants.KEY_INGREDIENTS);
        gson = new Gson();
        ingredientList = gson.fromJson(ingredientJson,
                new TypeToken<List<Ingredient>>() {
                }.getType());
        stepList = gson.fromJson(stepJson,
                new TypeToken<List<Step>>() {
                }.getType());
    }

}
