package com.example.eh.bakingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.StepItem;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.Callback,IngredientFragment.Callback{
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.DETAIL_RECIPE,
                    getIntent().getParcelableExtra(RecipeDetailFragment.DETAIL_RECIPE));

            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }

        if (findViewById(R.id.selected_container) != null) {
            mTwoPane = true;

         //   Toast.makeText(this, "mTwoPane = true", Toast.LENGTH_SHORT).show();
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.selected_container, new SelectedRecipeFragment(),
                                SelectedRecipeFragment.TAG)
                        .commit();
            }
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mTwoPane = false;
        //    Toast.makeText(this, " mTwoPane = false", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(StepItem stepItem, ArrayList<StepItem> stepsList, int pos ) {

        if(mTwoPane){
            Bundle arguments = new Bundle();
            arguments.putParcelable(SelectedRecipeFragment.Selected_RECIPE,
                    stepItem);
            arguments.putParcelableArrayList(SelectedRecipeFragment.RECIPES_LIST,stepsList);
            arguments.putString(SelectedRecipeFragment.RECIPE_POSITION, String.valueOf(pos));
            Fragment fragment = null;
            fragment = new SelectedRecipeFragment();
            fragment.setArguments(arguments);
            if(fragment!=null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.selected_container, fragment, SelectedRecipeFragment.TAG)
                        .commit();

            }
        }
        else {
            Intent intent = new Intent(this, SelectedRecipeActivity.class)
                    .putExtra(SelectedRecipeFragment.Selected_RECIPE, stepItem);
            intent.putExtra(SelectedRecipeFragment.RECIPES_LIST, stepsList);
            intent.putExtra(SelectedRecipeFragment.RECIPE_POSITION, String.valueOf(pos));

            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(IngredientItem stepItem, ArrayList<IngredientItem> stepsList, int pos) {

    }
}
