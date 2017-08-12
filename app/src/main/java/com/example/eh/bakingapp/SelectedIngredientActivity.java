package com.example.eh.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eh.bakingapp.models.RecipeItem;

public class SelectedIngredientActivity extends AppCompatActivity {
    private RecipeItem mRecipeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_ingredient);


        if (savedInstanceState == null) {
            Toast.makeText(this, "Activity not null", Toast.LENGTH_SHORT).show();
            mRecipeItem= getIntent().getParcelableExtra(Ingredient_list_Fragment.RECIPE_INGREDIENT);
            if(mRecipeItem.getName().isEmpty())
            {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            }
            Bundle arguments = new Bundle();
            arguments.putParcelable(Ingredient_list_Fragment.RECIPE_INGREDIENT,
                    getIntent().getParcelableExtra(Ingredient_list_Fragment.RECIPE_INGREDIENT));



/*            Ingredient_list_Fragment fragment = new Ingredient_list_Fragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_container, fragment)
                    .commit();*/
        }
    }
}
