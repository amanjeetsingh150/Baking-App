package com.developers.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developers.bakingapp.R;
import com.developers.bakingapp.VideoFragment;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        bundle = new Bundle();
        bundle = getIntent().getExtras();
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_fragment, videoFragment).commit();
    }
}
