package com.example.eh.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eh.bakingapp.models.StepItem;

import java.util.ArrayList;
import java.util.List;

public class SelectedRecipeActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);

        if (savedInstanceState == null) {
//            Toast.makeText(this, "Activity not null", Toast.LENGTH_SHORT).show();
            Bundle arguments = new Bundle();
            arguments.putParcelable(SelectedRecipeFragment.Selected_RECIPE,
                    getIntent().getParcelableExtra(SelectedRecipeFragment.Selected_RECIPE));
            arguments.putParcelableArrayList(SelectedRecipeFragment.RECIPES_LIST,
                    getIntent().getParcelableArrayListExtra(SelectedRecipeFragment.RECIPES_LIST));
            arguments.putString(SelectedRecipeFragment.RECIPE_POSITION,getIntent().getStringExtra(SelectedRecipeFragment.RECIPE_POSITION)
                  );


            SelectedRecipeFragment fragment = new SelectedRecipeFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.selected_container, fragment)
                    .commit();
        }
    }
}
