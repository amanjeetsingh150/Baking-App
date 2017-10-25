package com.developers.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.tab_list_recipe_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    public boolean getNoPane() {
        return mTwoPane;
    }
}
