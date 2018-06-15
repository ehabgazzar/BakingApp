package com.example.eh.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eh.bakingapp.models.RecipeItem;
import com.google.gson.Gson;

public class SelectedIngredientActivity extends AppCompatActivity {
    private RecipeItem mRecipeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_ingredient);
        Gson gson = new Gson();

        if (savedInstanceState == null) {
         //   Toast.makeText(this, "Activity not null", Toast.LENGTH_SHORT).show();
            mRecipeItem=getIntent().getParcelableExtra(RecipeDetailFragment.DETAIL_RECIPE);
            if (mRecipeItem == null) {
                mRecipeItem = gson.fromJson(getIntent().getStringExtra(IngredientFragment.RECIPE_INGREDIENT), RecipeItem.class);
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            } else {
          //      Toast.makeText(this, mRecipeItem.getName(), Toast.LENGTH_SHORT).show();
                Bundle arguments = new Bundle();
                arguments.putParcelable(IngredientFragment.RECIPE_INGREDIENT,
                        mRecipeItem);


                IngredientFragment fragment = new IngredientFragment();
                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredient_container, fragment)
                        .commit();
            }
        }
    }
}
