package com.example.eh.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eh.bakingapp.Adapters.RecipeAdapter;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.JsonParser;
import com.example.eh.bakingapp.utilities.NetHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {


    private RecipeAdapter mRecipeAdapter;
    private ArrayList<RecipeItem> mRecipesList = null;
    NetHelper mNetHelper;
    Context mContext;
    @BindView(R.id.gridview_recipes) GridView gridView;
    public MainFragment() {
        // Required empty public constructor
    }

    public interface Callback {
        void onItemSelected(RecipeItem recipeItem);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecipeAdapter =
                new RecipeAdapter(getActivity(), new ArrayList<RecipeItem>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mContext=this.getActivity();
        mNetHelper= NetHelper.getInstance(mContext);
        ButterKnife.bind(this,rootView);
        gridView.setAdapter(mRecipeAdapter);

        fetchData();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
                RecipeItem recipeItem = mRecipeAdapter.getItem(position);

                ((Callback) getActivity()).onItemSelected(recipeItem);
            }
        });

        return rootView;
    }


    void fetchData()
    {
        mNetHelper.jsonRequest("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Log.d("Request Response", response);
               mRecipesList=JsonParser.getInstnace(mContext).requests(response);
               mRecipeAdapter.setData(mRecipesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}
