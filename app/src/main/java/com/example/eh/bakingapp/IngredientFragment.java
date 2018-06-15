package com.example.eh.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eh.bakingapp.Adapters.CustomItemClickListener;
import com.example.eh.bakingapp.Adapters.IngredientAdapter;
import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Eh on 7/8/2017.
 */

public class IngredientFragment extends Fragment {
@BindView(R.id.recycler_view)RecyclerView recyclerView;
    public static final String RECIPE_INGREDIENT = "RECIPE_Ingredient";

    private ArrayList<IngredientItem> ingredientItems = new ArrayList<>();
    private RecipeItem mRecipeItem;
   // private RecyclerView recyclerView;
    private IngredientAdapter mAdapter;
    public static final String TAG = IngredientFragment.class.getSimpleName();
    static final String DETAIL_RECIPE = "DETAIL_Recipe";


    public IngredientFragment() {
        // Required empty public constructor
    }
    public interface Callback {
        void onItemSelected(IngredientItem stepItem, ArrayList <IngredientItem> stepsList, int pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mRecipeItem = arguments.getParcelable(IngredientFragment.RECIPE_INGREDIENT);
            if(mRecipeItem ==null)
            {
                mRecipeItem = arguments.getParcelable(RecipeDetailFragment.DETAIL_RECIPE);
            }





        }
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        ButterKnife.bind(this,rootView);

       // recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        recyclerView.setHasFixedSize(true);


        prepareStepsData();

        return rootView;
    }

    void prepareStepsData()
    {

        ingredientItems =mRecipeItem.getIngredientItems();
        Log.d("LEng",""+ ingredientItems.size());

        mAdapter = new IngredientAdapter(ingredientItems, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "Element " + position + " clicked.");

                IngredientItem stepItem=mAdapter.getItem(position);
                ((IngredientFragment.Callback) getActivity()).onItemSelected(stepItem, ingredientItems,position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



    }
}
