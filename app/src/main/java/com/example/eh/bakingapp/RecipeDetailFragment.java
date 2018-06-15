package com.example.eh.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eh.bakingapp.Adapters.CustomItemClickListener;
import com.example.eh.bakingapp.Adapters.RecipeDetailAdapter;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.models.StepItem;
import com.example.eh.bakingapp.utilities.DividerItemDecoration;
import com.example.eh.bakingapp.utilities.SharedGetter;
import com.example.eh.bakingapp.utilities.SharedSaver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Eh on 6/30/2017.
 */

public class RecipeDetailFragment extends Fragment {
    private ArrayList <StepItem> stepItems = new ArrayList<>();
    SharedGetter sharedGetter;
    private RecipeItem mRecipeItem;
    private RecyclerView recyclerView;
    private RecipeDetailAdapter mAdapter;
    public static final String TAG = RecipeDetailFragment.class.getSimpleName();
   public static final String DETAIL_RECIPE = "DETAIL_Recipe";
    TextView ingredients;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }
    public interface Callback {
        void onItemSelected(StepItem stepItem, ArrayList <StepItem> stepsList, int pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        sharedGetter = SharedGetter.getInstance(this.getContext());
        Bundle arguments = getArguments();
        if (arguments != null) {
            mRecipeItem = arguments.getParcelable(RecipeDetailFragment.DETAIL_RECIPE);
            Gson gson = new Gson();
            String recipeItem = gson.toJson(mRecipeItem);

            SharedSaver.getInstance(this.getContext()).setDesiredRecipe(recipeItem);

            Intent intent = new Intent(this.getActivity(), StackWidgetProvider.class);
            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            int ids[] = AppWidgetManager.getInstance(this.getActivity().getApplication()).getAppWidgetIds(new ComponentName(this.getActivity().getApplication(), StackWidgetProvider.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            this.getActivity().sendBroadcast(intent);
            AppWidgetManager mgr = AppWidgetManager.getInstance(this.getContext());
            StackWidgetProvider.updateAppWidget(this.getActivity(),mgr,ids);



         String gs=sharedGetter.getDesiredRecipe();
          RecipeItem ri=gson.fromJson(gs,RecipeItem.class);

            Toast.makeText(this.getActivity(), ri.getName(), Toast.LENGTH_LONG).show();



        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ingredients = (TextView)  rootView.findViewById(R.id.textView2);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bundle arguments = new Bundle();
                arguments.putParcelable(RecipeDetailFragment.DETAIL_RECIPE, mRecipeItem);

                Fragment fragment = null;
                fragment = new IngredientFragment();
                fragment.setArguments(arguments);
                if(fragment!=null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.replace(R.id.detail_container, fragment, IngredientFragment.TAG)
                            .commit();
                    ingredients.setVisibility(View.INVISIBLE);

                }
*/
                Intent intent = new Intent(getActivity(), SelectedIngredientActivity.class);
                intent.putExtra(RecipeDetailFragment.DETAIL_RECIPE, mRecipeItem);
                startActivity(intent);

            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        recyclerView.setHasFixedSize(true);


        prepareStepsData();

        return rootView;
    }

  void prepareStepsData()
  {

      stepItems=mRecipeItem.getStepItems();
      Log.d("LEng",""+stepItems.size());

      mAdapter = new RecipeDetailAdapter(getActivity(),stepItems, new CustomItemClickListener() {
          @Override
          public void onItemClick(View v, int position) {
              Log.d(TAG, "Element " + position + " clicked.");

              StepItem stepItem=mAdapter.getItem(position);
              ((Callback) getActivity()).onItemSelected(stepItem,stepItems,position);
          }
      });
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(mAdapter);



  }
}
