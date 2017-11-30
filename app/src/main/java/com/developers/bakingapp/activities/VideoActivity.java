package com.developers.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.developers.bakingapp.R;
import com.developers.bakingapp.VideoFragment;
import com.developers.bakingapp.util.Constants;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    private Bundle bundle;
    private boolean fragmentCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        if (savedInstanceState != null) {
            fragmentCreated = savedInstanceState.getBoolean(Constants.KEY_ROTATION_VIDEO_ACTIVITY);
        }
        if (!fragmentCreated) {
            //Only init when the bool is false and fragments need to be transacted
            //for preserving the ExoPlayer instance so that it resumes properly
            bundle = new Bundle();
            bundle = getIntent().getExtras();
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            fragmentCreated = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_fragment, videoFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Back Button to navigate back to the details screen
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.KEY_ROTATION_VIDEO_ACTIVITY, fragmentCreated);
    }
}
