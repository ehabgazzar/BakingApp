package com.example.eh.bakingapp.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.RecipeDetailFragment;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.JsonParser;
import com.example.eh.bakingapp.utilities.NetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eh on 7/17/2017.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
//    private final ImageDownloader imageDownloader = new ImageDownloader();
    private List<RecipeItem> recipeItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    NetHelper mNetHelper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        mNetHelper= NetHelper.getInstance(mContext);
    }

    public void onDestroy() {
        recipeItems.clear();
    }

    public int getCount() {
        return recipeItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.stackwidget_item);


            RecipeItem recipeItem = recipeItems.get(position);




                rv.setTextViewText(R.id.stackWidgetItemContent, recipeItem.getName());




            Bundle extras = new Bundle();
            extras.putParcelable(RecipeDetailFragment.DETAIL_RECIPE, recipeItem);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.stackWidgetItem, fillInIntent);


        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {

        mNetHelper.jsonRequest("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.d("Request Response", "HEREEEEEE");
                recipeItems = JsonParser.getInstnace(mContext).requests(response);
//                mRecipeAdapter.setData(mRecipesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}
