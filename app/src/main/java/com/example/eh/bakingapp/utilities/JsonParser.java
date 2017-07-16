package com.example.eh.bakingapp.utilities;

import android.content.Context;
import android.util.Log;

import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.models.StepItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eh on 6/23/2017.
 */

public class JsonParser {

    private static JsonParser mInstance;
    private static Context mContext;


    public static JsonParser getInstnace(Context context) {
        mContext = context;

        if (mInstance == null)
            mInstance = new JsonParser();

        return mInstance;
    }
    public ArrayList<RecipeItem> requests(String response) {
        try {
            JSONArray jsonArray= new JSONArray(response);


            ArrayList<RecipeItem> itemList = new ArrayList<>();

            for (int i = 0; i <jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                RecipeItem newItem = new RecipeItem();


//***************************************************************************************
                JSONArray ingredientArray= item.getJSONArray("ingredients");

                ArrayList<IngredientItem> IngredientItems = new ArrayList<>();

                for (int j=0; j<ingredientArray.length();j++)
                {
                    JSONObject Item = ingredientArray.getJSONObject(j);
                    IngredientItem ingredientItem = new IngredientItem();
                    ingredientItem.setIngredient(Item.getString("ingredient"));
                    ingredientItem.setMeasure(Item.getString("measure"));
                    ingredientItem.setQuantity(Item.getInt("quantity"));
                    IngredientItems.add(ingredientItem);

                }

//***************************************************************************************
                JSONArray stepArray= item.getJSONArray("steps");

                ArrayList<StepItem> StepItems = new ArrayList<>();

                for (int k=0; k<stepArray.length();k++)
                {
                    JSONObject Item = stepArray.getJSONObject(k);
                    StepItem stepItem = new StepItem();
                    stepItem.setId(Item.getInt("id"));
                    stepItem.setDescription(Item.getString("description"));
                    stepItem.setShortDescription(Item.getString("shortDescription"));
                    stepItem.setVideoURL(Item.getString("videoURL"));
                    StepItems.add(stepItem);

                }


//***************************************************************************************
                newItem.setId(item.getInt("id"));
                newItem.setName(item.getString("name"));
                newItem.setIngredientItems(IngredientItems);
                newItem.setStepItems(StepItems);
                newItem.setServings(item.getString("servings"));
                itemList.add(newItem);
            }
                      Log.e("JSOBN","DOOOOOONEEEE");
            return itemList;



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
