package com.example.eh.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.RecipeItem;
import com.example.eh.bakingapp.models.StepItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Eh on 6/30/2017.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.MyViewHolder>  {
    private ArrayList <StepItem> stepsList;
    //View.OnClickListener onClickListener;
    CustomItemClickListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.recipe_detail_name)TextView title ;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }
    }

    public RecipeDetailAdapter(ArrayList<StepItem> stepsList, CustomItemClickListener listener) {

        this.stepsList = stepsList;
        this.listener=listener;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_detail_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);
       /* itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " +  myViewHolder.getAdapterPosition() + " clicked.");


            }
        });*/

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, myViewHolder.getAdapterPosition());
            }
        });

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StepItem stepItem = stepsList.get(position);
        holder.title.setText(stepItem.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public  StepItem getItem(int position) {
        return stepsList.get(position);
    }
}
