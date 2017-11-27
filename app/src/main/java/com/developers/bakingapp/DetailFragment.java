package com.developers.bakingapp;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.bakingapp.activities.VideoActivity;
import com.developers.bakingapp.adapters.VideoAdapter;
import com.developers.bakingapp.model.Ingredient;
import com.developers.bakingapp.model.Result;
import com.developers.bakingapp.model.Step;
import com.developers.bakingapp.util.ClickCallBack;
import com.developers.bakingapp.util.Constants;
import com.developers.bakingapp.widget.RecipeAppWidgetProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements ClickCallBack {

    private static final String TAG = DetailFragment.class.getSimpleName();
    @BindView(R.id.ingredients_list_text_view)
    TextView ingredientsText;
    @BindView(R.id.step_recycler_view)
    RecyclerView stepRecyclerView;
    String steps, ingredients;
    Gson gson;
    VideoAdapter videoAdapter;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private boolean twoPane, menuVisible;

    public DetailFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        steps = bundle.getString(Constants.KEY_STEPS_JSON);
        ingredients = bundle.getString(Constants.KEY_INGREDIENTS_JSON);
        gson = new Gson();
        ingredientList = gson.fromJson(ingredients,
                new TypeToken<List<Ingredient>>() {
                }.getType());
        stepList = gson.fromJson(steps,
                new TypeToken<List<Step>>() {
                }.getType());
        twoPane = bundle.getBoolean(Constants.KEY_PANE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            menuVisible = savedInstanceState.getBoolean("menuVisibility");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Ingredient ingredient : ingredientList) {
            stringBuffer.append("\u2022 " + ingredient.getQuantity() + " " +
                    ingredient.getIngredient() + " " + ingredient.getMeasure() + "\n");
        }
        setHasOptionsMenu(true);
        ingredientsText.setText(stringBuffer.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stepRecyclerView.setLayoutManager(linearLayoutManager);
        Log.d(TAG, stepList.size() + "");
        videoAdapter = new VideoAdapter(getActivity(), stepList);
        videoAdapter.setOnClick(this);
        stepRecyclerView.setAdapter(videoAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(Context context, Integer id, String description, String url) {
        if (twoPane) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.KEY_STEPS_ID, id);
            bundle.putString(Constants.KEY_STEPS_DESC, description);
            bundle.putString(Constants.KEY_STEPS_URL, url);
            bundle.putBoolean(Constants.KEY_PANE_VID, twoPane);
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container_tab, videoFragment).commit();
        } else {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra(Constants.KEY_STEPS_ID, id);
            intent.putExtra(Constants.KEY_STEPS_DESC, description);
            intent.putExtra(Constants.KEY_STEPS_URL, url);
            context.startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!menuVisible) {
            inflater.inflate(R.menu.main_menu, menu);
            menuVisible = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("menuVisibility", menuVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.action_add_widget:
                    SharedPreferences sharedPreferences = getActivity()
                            .getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    Result result = gson.fromJson(sharedPreferences.getString(Constants.WIDGET_RESULT, null), Result.class);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                    Bundle bundle = new Bundle();
                    int appWidgetId = bundle.getInt(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID);
                    RecipeAppWidgetProvider.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, result.getName(),
                            result.getIngredients());
                    Toast.makeText(getActivity(), "ADDED " + result.getName() + " to Widget.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
