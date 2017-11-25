package com.developers.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.developers.bakingapp.R;
import com.developers.bakingapp.model.Ingredient;
import com.developers.bakingapp.model.Result;
import com.developers.bakingapp.util.Constants;
import com.google.gson.Gson;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidgetProvider extends AppWidgetProvider {

    SharedPreferences sharedPreferences;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<Ingredient> ingredientList) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.recipe_name_text_view, recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);
        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_list_item);
            ingredientView.setTextViewText(R.id.ingredient_name_text_view,
                    String.valueOf(ingredient.getIngredient()) + " " +
                            String.valueOf(ingredient.getMeasure()));
            views.addView(R.id.widget_ingredients_container, ingredientView);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(Constants.WIDGET_RESULT, null);
        Gson gson = new Gson();
        Result recipe = gson.fromJson(result, Result.class);
        String recipeName = recipe.getName();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, recipe.getIngredients());
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

