package com.example.eh.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eh on 8/19/2017.
 */

public class SharedGetter  {
    private static SharedGetter mInstance;
    private static SharedPreferences mShared;

    private SharedGetter() {
    }


    /**
     * Get an Instance from NetHelper in all the project (Signleton pattern).
     *
     * @return NetHelper
     */
    public static SharedGetter getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedGetter();

        mShared = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return mInstance;
    }

    public SharedPreferences getShared() {
        return mShared;
    }
    public String getDesiredRecipe(){
        return mShared.getString("Recipe","");
    }
}
