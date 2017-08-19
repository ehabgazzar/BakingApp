package com.example.eh.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Eh on 8/19/2017.
 */

public class SharedSaver {


    private static SharedSaver mInstance ;
    static Context mContext;
    static SharedPreferences SHARED ;

    public static SharedSaver getInstance(Context context)
    {
        if (mInstance == null)
            mInstance = new SharedSaver();

        mContext = context ; // will change everytime we need to get instance.

        if(SHARED == null)
            SHARED   = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);

        return mInstance;
    }


    public void  setDesiredRecipe(String recipe)
    {
        SharedPreferences.Editor editor = SHARED.edit();
        editor.putString("Recipe",recipe);
        editor.apply();
        Log.d("Recipe","Add to sharedpref");
    }
}
