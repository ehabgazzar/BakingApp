package com.example.eh.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.JsonParser;
import com.example.eh.bakingapp.utilities.NetHelper;

import java.util.ArrayList;

/**
 * Created by Eh on 7/16/2017.
 */
public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

 class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    NetHelper mNetHelper;
    private ArrayList<RecipeItem> mRecipesList = null;
    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }
    @Override
    public void onCreate() {
        mNetHelper= NetHelper.getInstance(mContext);
        Log.d("ON CREATE","1");
    }

    @Override
    public void onDataSetChanged() {
        fetchData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        return mRecipesList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mRecipesList == null || mRecipesList.size()== 0) return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.widget_recipe_name,mRecipesList.get(position).getName());
            Log.d("ABCA","1");
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    void fetchData()
    {
        mNetHelper.jsonRequest("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Log.d("Request Response", response);
                mRecipesList= JsonParser.getInstnace(mContext).requests(response);
//                mRecipeAdapter.setData(mRecipesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
