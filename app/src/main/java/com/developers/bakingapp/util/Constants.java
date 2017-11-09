package com.developers.bakingapp.util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amanjeet Singh on 24/10/17.
 */

public class Constants {
    public static final String BASE_URL = "http://d17h27t6h515a5.cloudfront.net/";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_STEPS_JSON = "stepListJson";
    public static final String KEY_INGREDIENTS_JSON = "ingredientsListJson";
    public static final String KEY_STEPS_ID = "stepId";
    public static final String KEY_STEPS_DESC = "stepDescription";
    public static final String KEY_STEPS_URL = "stepURL";
    public static final String KEY_PANE = "twoPane";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
