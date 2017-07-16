package com.example.eh.bakingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eh.bakingapp.models.RecipeItem;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment= new MainFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.container, mainFragment)
                .commit();

    }

    @Override
    public void onItemSelected(RecipeItem recipeItem) {
        Intent intent = new Intent(this, RecipeDetailActivity.class)
                .putExtra(RecipeDetailFragment.DETAIL_RECIPE, recipeItem);
        startActivity(intent);
    }
}
