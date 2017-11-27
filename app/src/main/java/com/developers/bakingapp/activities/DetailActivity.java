package com.developers.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.developers.bakingapp.DetailFragment;
import com.developers.bakingapp.R;
import com.developers.bakingapp.VideoFragment;
import com.developers.bakingapp.util.Constants;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private boolean twoPane;
    String stepJson, ingredientJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (findViewById(R.id.video_container_tab) != null) {
            twoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.video_container_tab, new VideoFragment()).commit();
        }
        stepJson = getIntent().getStringExtra(Constants.KEY_STEPS);
        ingredientJson = getIntent().getStringExtra(Constants.KEY_INGREDIENTS);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_STEPS_JSON, stepJson);
        bundle.putString(Constants.KEY_INGREDIENTS_JSON, ingredientJson);
        bundle.putBoolean(Constants.KEY_PANE, twoPane);
        Log.d(TAG, "Panes " + twoPane);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_fragment, detailFragment).commit();
    }
}
