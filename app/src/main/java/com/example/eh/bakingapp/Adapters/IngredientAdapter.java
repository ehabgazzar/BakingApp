package com.example.eh.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eh.bakingapp.R;
import com.example.eh.bakingapp.models.IngredientItem;
import com.example.eh.bakingapp.models.StepItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eh on 7/8/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder>{
    private ArrayList<IngredientItem> ingredientsList;
    //View.OnClickListener onClickListener;
    CustomItemClickListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.textView9)TextView title;
        @BindView(R.id.textView8)TextView values;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

        }
    }


    public IngredientAdapter(ArrayList<IngredientItem> ingredientsList, CustomItemClickListener listener) {

        this.ingredientsList = ingredientsList;

    }
    public IngredientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        final IngredientAdapter.MyViewHolder myViewHolder = new IngredientAdapter.MyViewHolder(itemView);



        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(IngredientAdapter.MyViewHolder holder, int position) {
        IngredientItem ingredientItem = ingredientsList.get(position);
        holder.title.setText(ingredientItem.getIngredient());
        holder.values.setText(ingredientItem.getQuantity()+" "+ingredientItem.getMeasure());

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public  IngredientItem getItem(int position) {
        return ingredientsList.get(position);
    }
}
