package com.example.eh.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eh.bakingapp.Adapters.CustomItemClickListener;
import com.example.eh.bakingapp.Adapters.RecipeAdapter;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.utilities.DividerItemDecoration;
import com.example.eh.bakingapp.utilities.JsonParser;
import com.example.eh.bakingapp.utilities.NetHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<RecipeItem> mRecipesList = null;
    NetHelper mNetHelper;
    Context mContext;
    private RecyclerView recyclerView;

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


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mContext=this.getActivity();
        mNetHelper= NetHelper.getInstance(mContext);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridview_recipes);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        recyclerView.setHasFixedSize(true);


        ConnectivityManager conMgr =  (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(mContext, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
        else {
            fetchData();
        }

        return rootView;
    }


    void fetchData()
    {
        mNetHelper.jsonRequest("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Log.d("Request Response", response);
               mRecipesList=JsonParser.getInstnace(mContext).requests(response);
                mRecipeAdapter=new RecipeAdapter(getActivity(),mRecipesList,new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d(TAG, "Element " + position + " clicked.");

                        RecipeItem recipeItem=mRecipeAdapter.getItem(position);
                        ((Callback) getActivity()).onItemSelected(recipeItem);
                    }
                });
             //  mRecipeAdapter.setData(mRecipesList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mRecipeAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error in loading please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
