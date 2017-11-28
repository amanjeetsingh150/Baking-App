package com.developers.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developers.bakingapp.activities.DetailActivity;
import com.developers.bakingapp.R;
import com.developers.bakingapp.model.Ingredient;
import com.developers.bakingapp.model.Result;
import com.developers.bakingapp.model.Step;
import com.developers.bakingapp.util.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amanjeet Singh on 25/10/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Result> resultList;
    private String servings;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private Intent intent;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public RecipeAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        holder.dishText.setText(resultList.get(position).getName());
        servings = context.getString(R.string.servings) + " " +
                String.valueOf(resultList.get(position).getServings());
        String imageUrl = resultList.get(position).getImage();
        if (!imageUrl.equals("")) {
            //Load image if present
            Picasso.with(context).load(imageUrl).into(holder.imageView);
        }
        holder.servingText.setText(servings);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientList = resultList.get(position).getIngredients();
                stepList = resultList.get(position).getSteps();
                intent = new Intent(context, DetailActivity.class);
                gson = new Gson();
                String ingredientJson = gson.toJson(ingredientList);
                String stepJson = gson.toJson(stepList);
                intent.putExtra(Constants.KEY_INGREDIENTS, ingredientJson);
                intent.putExtra(Constants.KEY_STEPS, stepJson);
                String resultJson = gson.toJson(resultList.get(position));
                sharedPreferences.edit().putString(Constants.WIDGET_RESULT, resultJson).apply();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_text_view)
        TextView dishText;

        @BindView(R.id.servings_text_view)
        TextView servingText;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.dish_image_view)
        AppCompatImageView imageView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
