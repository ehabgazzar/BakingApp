package com.example.eh.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.eh.bakingapp.Adapters.StackWidgetService;
import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.SharedGetter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class StackWidgetProvider extends AppWidgetProvider {
     SharedGetter sharedGetter;
    private List<IngredientItem> ingredientItems = new ArrayList<>();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d("AppWidgetProvider","Called");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stack_widget_provider);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
//        appWidgetManager.getInstance(context).updateAppWidget(
//                new ComponentName(context, StackWidgetProvider.class), views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        int[] appWidgetIds;
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
             appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            //int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
         //   Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            for(int i = 0 ; i<appWidgetIds.length;i++) {
                updateAppWidget(context, mgr, appWidgetIds[i]);
            }
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("onUpdate","Called");
        sharedGetter = SharedGetter.getInstance(context);
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.stack_widget_provider);

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, StackWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored

            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.stackWidgetView, serviceIntent);
            remoteViews.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);
            Gson gson = new Gson();
            String gs=sharedGetter.getDesiredRecipe();
            RecipeItem ri=gson.fromJson(gs,RecipeItem.class);
            if(ri!=null)
                ingredientItems =ri.getIngredientItems();

            StringBuffer buff = new StringBuffer();

            for (int j = 0; j < ingredientItems.size(); j++) {
                IngredientItem item = ingredientItems.get(j);
                buff.append("-> " + item.getIngredient() + "\n    Quantity : " + item.getQuantity() + " " + item.getMeasure() + "\n");
            }
            remoteViews.setTextViewText(R.id.stackWidgetItemContent, buff);
         //   remoteViews.setTextViewText(R.id.stackWidgetItemContent2, ingredientItem.getQuantity()+" "+ingredientItem.getMeasure());

            // set intent for item click (opens main activity)
//            Intent viewIntent = new Intent(context, SelectedIngredientActivity.class);
//            viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
//            viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
//
//            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
//            remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);

            // update widget

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
//            AppWidgetManager.getInstance(context).updateAppWidget(
//                    new ComponentName(context, StackWidgetProvider.class), remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

