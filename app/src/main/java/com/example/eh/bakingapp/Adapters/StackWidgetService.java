package com.example.eh.bakingapp.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.JsonParser;
import com.example.eh.bakingapp.utilities.NetHelper;
import com.example.eh.bakingapp.utilities.SharedGetter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private List<IngredientItem> ingredientItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    SharedGetter sharedGetter;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        sharedGetter = SharedGetter.getInstance(mContext);
      //  mNetHelper= NetHelper.getInstance(mContext);
     //   fetchData();
    }

    public void onDestroy() {
        ingredientItems.clear();
    }

    public int getCount() {
        return ingredientItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.stackwidget_item);


            IngredientItem ingredientItem = ingredientItems.get(position);




                rv.setTextViewText(R.id.stackWidgetItemContent,"-> " + ingredientItem.getIngredient() + "\n    Quantity : " + ingredientItem.getQuantity() + " " + ingredientItem.getMeasure() + "\n");
                //rv.setTextViewText(R.id.stackWidgetItemContent2, ingredientItem.getQuantity()+" "+ingredientItem.getMeasure());



//        Gson gson = new Gson();
//        String myJson = gson.toJson(ingredientItem);

//            Intent fillInIntent = new Intent();
//            fillInIntent.putExtra(Ingredient_list_Fragment.RECIPE_INGREDIENT, myJson);
//            rv.setOnClickFillInIntent(R.id.stackWidgetItemContent, fillInIntent);


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
        Gson gson = new Gson();
        String gs=sharedGetter.getDesiredRecipe();
        RecipeItem ri=gson.fromJson(gs,RecipeItem.class);
        if(ri!=null)
        ingredientItems =ri.getIngredientItems();
        // fetchData();

    }


    String  getData() {
         final String LOG_TAG = StackWidgetService.class.getSimpleName();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {
            final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .build();

            URL url = new URL(builtUri.toString());


            // Create the request to TheMovie DB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Json Strings" + forecastJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        return forecastJsonStr;
    }

}
